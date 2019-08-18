layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    form.on("submit(add)", function(data) {
        var attribution1 = $("#attribution1").val();
        var attribution2 = $("#attribution2").val();
        var attribution3 = $("#attribution3").val();
        var attributionsName = attribution1 + ";" + attribution2 + ";" + attribution3;
        var data = {"name": data.field.name,"englishTitle":data.field.englishTitle,"attributionsName":attributionsName};
        var a = checkName();
        if (!a) {
            return false;
        }
        $.ajax({
            url: "/admin/ArticleType/addArticleType",
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
                url: "/admin/ArticleType/checkName",
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
});