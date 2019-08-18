layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/floor/view',
        title: '楼层列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'hotelName', title: '酒店名称', align: 'center'},
                {field: 'name', title: '楼层名称', align: 'center'},
                {field: 'remarks', title: '备注', align: 'center'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item'}
            ]
        ],
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
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
        var name = data.field.name;

        // 表格重新加载
        tableIns.reload({
            where: {
                name: name
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
                    content: "/admin/floor/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/floor/delete",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (res) {
                            if (!res || res.errcode != 0) {
                                layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
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
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data; // 获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: "添加",
                    content: "/admin/floor/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
        ;
    });
});