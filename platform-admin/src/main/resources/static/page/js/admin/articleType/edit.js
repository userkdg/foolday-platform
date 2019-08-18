layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var nameTmp;
    function getAttribution(attributions, index) {
        var attributionArray = attributions.toString().split(";");
        return attributionArray[index];
    }
    function init() {
        getInitData();
    }
    init();

    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/ArticleType/getInfo",
            data: "id=" + primaryKey,
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    $("#primaryKey").val(initData.id);
                    $("#name").val(initData.name);
                    $("#englishTitle").val(initData.englishTitle);
                    $("#attribution1").val(getAttribution(initData.attributionsName, 0));
                    $("#attribution2").val(getAttribution(initData.attributionsName, 1));
                    $("#attribution3").val(getAttribution(initData.attributionsName, 2));
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
        var attribution1 = $("#attribution1").val();
        var attribution2 = $("#attribution2").val();
        var attribution3 = $("#attribution3").val();
        var attributionsName = attribution1 + ";" + attribution2 + ";" + attribution3;
        var id = $("#primaryKey").val();
        var name = $("#name").val();
        var englishTitle = $("#englishTitle").val();
        if(name != nameTmp) {
            var a = checkName();
            if (!a) {
                return false;
            }
        }
        $.ajax({
            url: "/admin/ArticleType/update",
            data: JSON.stringify({
                id: id.toString(),
                name: name,
                englishTitle: englishTitle,
                attributionsName: attributionsName
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
