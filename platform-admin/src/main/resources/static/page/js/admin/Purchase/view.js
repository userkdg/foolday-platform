layui.use(['table', 'layer', "jquery", "laydate", "util", "form", "upload"], function () {
    var table = layui.table;
    var form = layui.form;
    var util = layui.util;
    var $ = layui.jquery;
    var laydate=layui.laydate;
    var upload = layui.upload;

    laydate.render({
        elem: '#activityTime'
        ,type: 'datetime'
    });

    form.on("submit(add_activity)", function (obj) {
        var data = obj.field;
        $.ajax({
            url: "/admin/activityReservation/save",
            method: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                console.log(res);
                res = JSON.parse(res);
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.errmsg : "请求出错");
                    return false;
                }

                location.reload();

            }
        });

        return false;
    })

    table.render({
        elem: '#dataView',
        url: '/admin/Purchase/findAll',
        title: '购买套餐信息表',
        method: 'get',
        page: true,
        limit: 15,
        limits: [5, 15, 25],
        cols: [
            [
                {type: 'numbers', width: 80, align: "center", title: '序号'},
                {field: 'comboName', title: '套餐名称', align: 'center'},
                {field: 'cardNumber', title: '卡号', align: 'center'},
                {field: 'phone', title: '手机号码', align: 'center'},
                {field: 'mayUseFrequency', title: '联系电话', align: 'center'},
                {field: 'price', title: '价格', align: 'center'},
                {field: 'state', title: '状态', align: 'center'},
                {field: 'verificationCode', title: '核销码', align: 'center'},
                {field: 'createTime',title:'购买时间', align: 'center'},
                {field: 'status',title:'冻结', align: 'center'},
                {field: 'orderNo', title: '订单编号', align: 'center'},
                {field: 'type', title: '价格', align: 'center'},
                {field: 'usageRecord', title: '使用记录', align: 'center'},
                {field: 'reservationNumber', title: '预订单号', align: 'center'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: '20%'}
            ]
        ],
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错");
                return {};
            }

            res.data = res.data.map(function (item) {
                item.activityTime = util.toDateString(item.activityTime, "yyyy-MM-dd HH:mm:ss");
                item.submitTime = util.toDateString(item.submitTime, "yyyy-MM-dd HH:mm:ss");
                return item;
            });


            return {
                "code": res.errcode,
                "data": res.data,
                "count": res.count,
                "size": 15
            }
        }
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
           /* case 'edit':
                layer.open({
                    type: 2,
                    title: "编辑",
                    content: "/admin/activityReleased/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;*/
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/activityReleased/delReleased?id=" + data.id,
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (result) {
                            if (!result || result.errcode != 0) {
                                layer.msg(res ? res.errmsg : '网络异常,请稍后再试');
                                return;
                            }
                            location.reload();
                        }
                    });
                });
                break;
        }
        ;
    });

/*    $(".modal").click(function () {
        layer.open({
            title: "添加预约",
            content: $(".add-activity"),
            type: 1,
            area: ["700px", "550px"]
        });

        form.render("select");
    })*/

});

