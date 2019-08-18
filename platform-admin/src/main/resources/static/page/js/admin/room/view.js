function convertStatus(status) {
    var statusDesc = "";
    if (status == 0) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">未预定</button>';
    } else if(status == 1){
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">已预定</button>';
    }  else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">已入住</button>';
    }
    return statusDesc;
};


layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/room/view',
        title: '房间列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'hotelName', title: '酒店名称', align: 'center'},
                {field: 'houseName', title: '房型名称', align: 'center'},
                {field: 'name', title: '房间号', align: 'center'},
                {field: 'userName', title: '预订人', align: 'center'},
                {
                    field: 'status', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.status)}}</div>'
                },
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item'}
            ]
        ],
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.errmsg, {icon: 2, time: 5000}, function () {
                });
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

    form.on("submit(query)", function (data) {
        var name = data.field.name;
        var status = data.field.status;
        var userName = data.field.userName;
        // 表格重新加载
        tableIns.reload({
            where: {
                name: name,
                userName: userName,
                status: status
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
                    content: "/admin/room/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/room/delete",
                        data: {
                            ids: data.id
                        },
                        type: "post",
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
        };
    });

    table.on('toolbar(data)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data; // 获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: "添加",
                    content: "/admin/room/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
        ;
    });
});