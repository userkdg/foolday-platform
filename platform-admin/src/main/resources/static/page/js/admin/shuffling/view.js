layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/hotelArea/view',
        title: '酒店地区列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'province', title: '省份', align: 'center'},
                {field: 'city', title: '城市', align: 'center'},
                {field: 'sequence', title: '排序序号', align: 'center'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: "35%"}
            ]
        ],
        parseData: function (res) {
            if(!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错!" , {icon: 2, time: 10000});
                return;
            }
            return {
                "code": res.errcode,
                "data": res.data,
                "count": res.count,
                "limit": 5
            }
        }
    });

    form.on("submit(query)", function (data) {
        var province = data.field.province;
        var city = data.field.city;

        // 表格重新加载
        tableIns.reload({
            where: {
                province: province,
                city: city
            }
        });
        return false;
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'edit':
                layer.open({
                    type: 2,
                    title: "编辑",
                    content: "../../admin/hotelArea/edit.html?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/hotelArea/delete",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (res) {
                            if(!res || res.errcode != 0) {
                                layer.msg(res ? res.errmsg : "请求出错!" , {icon: 2, time: 10000});
                                return;
                            }
                            location.reload();
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
        }
        ;
    });

    table.on('toolbar(data)', function (obj) {
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: "添加",
                    content: "../../admin/shuffling/add.html",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        };
    });
});