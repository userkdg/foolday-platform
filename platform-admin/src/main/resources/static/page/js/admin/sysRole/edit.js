layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    function convertStatus(status) {
        var statusDesc = "";
        if (status == true) {
            statusDesc = "true";
        } else {
            statusDesc = "false";
        }
        return statusDesc;
    };

    function init() {
        getInitData();
    }
    init();

    var nameTmp;
    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/sysRole/getInfo",
            data: "id=" + primaryKey,
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    $("#primaryKey").val(initData.id);
                    $("#name").val(initData.name);
                    $("#description").val(initData.description);
                    $("#available").val(convertStatus(initData.available));
                    nameTmp = initData.name;
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
        doSubmit();
        return false;
    });

    function doSubmit() {
        var name =  $('#name').val();
        var id =$('#primaryKey').val();
        var description = $('#description').val();
        var available = $('#available').val();
        if(name != nameTmp) {
            var a = checkName();
            if (!a) {
                return false;
            }
        }
        $.ajax({
            url: "/admin/sysRole/update",
            data: JSON.stringify({
                id: id.toString(),
                name: name,
                description: description,
                available: available,
            }),
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
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
    }

    function checkName() {
        var flag = false;
        var name = $("#name").val();
        if (name != null && name.length > 0) {
            $.ajax({
                url: "/admin/sysRole/checkName",
                data: "name=" + name,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 0) {
                        layer.tips("角色名已存在", '#name', {tips: [3, 'red']});
                        $('name').focus();
                    } else {
                        flag = true;
                    }
                },
                error: function (e) {
                    layer.msg('网络异常！', new Function());
                }
            });
        } else {
            flag = true;
        }
        return flag;
    };
});
