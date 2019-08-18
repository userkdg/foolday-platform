function convertImg(imgUrl, pixel) {
    var imgLable = '<img class="layui-upload-img" src="'+ imgUrl +'" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
    return imgLable;
};

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
        url: '/admin/sysRole/findAllRole',
        title: '角色列表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'name', title: '角色名', align: 'center'},
                {field: 'description', title: '角色描述', align: 'center'},
                {field: 'available', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.available)}}</div>'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width:"30%"}
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
                    title: "修改",
                    content: "/admin/sysRole/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？删除后不可再更改!', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/sysRole/delete",
                        data:"id=" + data.id,
                        type: "get",
                        dataType: "json",
                        success: function (result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case 'enable':
                layer.confirm('确认激活吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/sysRole/activate",
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
                        url: "/admin/sysRole/prohibit",
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
            case 'permissionEdit':
                layer.open({
                    type: 2,
                    title: "修改",
                    content: "/admin/sysRole/permissionEdit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
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
                    content: "/admin/sysRole/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
    });
});
