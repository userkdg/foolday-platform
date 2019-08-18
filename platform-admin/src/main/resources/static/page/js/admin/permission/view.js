function convertStatus(status) {
    var statusDesc = "";
    if (status == true) {
        statusDesc = "正常";
    } else {
        statusDesc = "禁用";
    }
    return statusDesc;
};

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;


    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/sysPermission/findPermissions',
        title: '权限列表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'name', title: '权限名', align: 'center'},
                {field: 'url', title: 'URL', align: 'center'},
                {field: 'description', title: '权限描述', align: 'center'},
                {field: 'available', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.available)}}</div>'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width:"25%"}
            ]
        ],
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.errcode,
                "data": res.data,
                "count": res.count,
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
                    content: "/admin/sysPermission/edit?id=" + data.id,
                    resize: false,
                    area: ['500px', '500px']
                });
                break;
            case 'enable':
                layer.confirm('确认激活吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/sysPermission/activate",
                        data: "id=" + data.id,
                        type: "get",
                        dataType: "json",
                        success: function(result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error:function(e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case 'prohibit':
                layer.confirm('确认禁用吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/sysPermission/prohibit",
                        data: "id=" + data.id,
                        type: "get",
                        dataType: "json",
                        success: function(result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error:function(e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
        }
        ;
    });
});
