layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;

    table.render({
        elem: '#dataView',
        url: '/admin/userLevel/findAll',
        title: '会员等级表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        id: "dataView",
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'name', title: '会员等名', align: 'center'},
                {field: 'requiredPoints', title: '所需积分', align: 'center'},
                {title: '操作', fixed: "right", align: 'left', toolbar: '#operator_item', width: "20%"}
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
                "count": res.pages,
                "limit": 5
            }
        }
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'edit':
                layer.open({
                    type: 2,
                    title: "编辑",
                    content: "/admin/userLevel/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗?', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/userLevel/delete",
                        data: "id=" + data.id,
                        type: "get",
                        dataType: "json",
                        success: function (res) {
                            if(!res || res.errcode != 0) {
                                layer.msg(res ? res.errmsg : "请求出错!" , {icon: 2, time: 1000});
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
    });

    table.on('toolbar(data)', function (obj) {
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: "添加",
                    content: "/admin/userLevel/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
        ;
    });
});