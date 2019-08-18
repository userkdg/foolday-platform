layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var nameTemp;
    function init() {
        getInitData();
    }
    init();

    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/room/getInfo",
            data: {
                id: primaryKey
            },
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    nameTemp = initData.name;
                    $("[name='id']").val(initData.id);
                    $("[name='name']").val(initData.name);
                    $("[name='userName']").val(initData.userName);

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
        if (name != nameTemp) {
            var a = checkName();
            if (!a) {
                return false;
            }
        }
        var userName = data.field.userName;
        var a = checkName();
        if (!a) {
            return false;
        }
        var id = $('#primaryKey').val();
        $.ajax({
            url: "/admin/room/edit",
            data: {
                id : id,
                name : name,
                userName : userName
            },
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
    });

    function checkName() {
        var flag = false;
        var name = $("#name").val();
        if (name != null && name.length > 0) {
            $.ajax({
                url: "/admin/room/checkName",
                data: {
                    name: name
                },
                type: "get",
                async: false,
                dataType: "json",
                success: function(result) {
                    if (result && result.errcode == 400) {
                        layer.tips("该名称已存在，请勿重复添加", '#name', {tips: [3, 'red']});
                        $('name').focus();
                    } else {
                        flag = true;
                    }
                },
                error:function(e) {
                    layer.msg('网络异常！', new Function());
                }
            });
        } else {
            flag = true;
        }
        return flag;
    };
});