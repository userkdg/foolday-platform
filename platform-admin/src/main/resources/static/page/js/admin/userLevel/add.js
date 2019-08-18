layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    form.on("submit(add)", function(data) {
        var data = {"name": data.field.name,"requiredPoints":data.field.requiredPoints};
        var a = checkName();
        if (!a) {
            return false;
        }
        a = checkPoints();
        if (!a) {
            return false;
        }
        $.ajax({
            url: "/admin/userLevel/add",
            data: JSON.stringify(data),
            contentType: 'application/json',
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
                url: "/admin/userLevel/checkName",
                data: "name=" + name,
                type: "get",
                async: false,
                dataType: "json",
                success: function(result) {
                    if (result && result.errcode == 0) {
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

    function checkPoints() {
        var flag = false;
        var points = $("#requiredPoints").val();
        if (points != null && points.length > 0) {
            $.ajax({
                url: "/admin/userLevel/checkRequiredPoints",
                data: "points=" + points,
                type: "get",
                async: false,
                dataType: "json",
                success: function(result) {
                    if (result && result.errcode == 0) {
                        layer.tips("该会员积分已存在，请勿重复添加", '#requiredPoints', {tips: [3, 'red']});
                        $('requiredPoints').focus();
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