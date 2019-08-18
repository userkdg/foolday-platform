layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    function init() {
        getInitData();
    }
    init();

    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/sysPermission/getInfo",
            data: "id=" + primaryKey,
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    $("#primaryKey").val(initData.id);
                    $("#name").val(initData.name);
                    $("#url").val(initData.url);description
                    $("#description").val(initData.description);
                    form.render(); // 重新绘制表单，让修改生效
                } else {
                    layer.msg('数据加载异常！', new Function());
                }
            },
            error:function(e) {
                layer.msg('网络异常！', new Function());
            }
        });
    }

    form.on("submit(edit)", function (data) {
        var name = data.field.name;
        var url = data.field.url;
        var description = data.field.description;
        var id = $('#primaryKey').val();
        $.ajax({
            url: "/admin/sysPermission/update",
            data: {
                id : id.toString(),
                description : description,
                name : name,
                url: url
            },
            type: "post",
            async: false,
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.location.reload();
                        parent.layer.close(index);
                    });
                } else {
                    layer.msg("操作失败", {icon: 2, time: 1000});
                }
            },
            error: function(e) {
                layer.msg('网络异常,请稍后再试', new Function());
            }
        });
        return false;
    });
});