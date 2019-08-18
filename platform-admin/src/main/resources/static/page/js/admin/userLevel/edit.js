layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    var nameTmp, pointTmp;
    var initData; // 回显的数据
    var primaryKey = $("#primaryKey").val();
    $.ajax({
        url: "/admin/userLevel/getInfo",
        data: "id=" + primaryKey,
        type: "get",
        dataType: "json",
        success: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
                return;
            }
            initData = res.data;
            form.val("userLevel_info", {
                id: initData.id,
                name: initData.name,
                requiredPoints: initData.requiredPoints
            });
            nameTmp = initData.name;
            pointTmp = initData.requiredPoints;
            // form.render(); // 重新绘制表单，让修改生效*/
        },
        error: function (e) {
            layer.msg('网络异常！', new Function());
        }
    });

    form.on("submit(edit)", function (data) {
        var name = data.field.name;
        var requiredPoints = data.field.requiredPoints;
        var a = checkName(nameTmp, name);
        if (!a) {
            return false;
        }
        a = checkPoints(pointTmp, requiredPoints);
        if (!a) {
            return false;
        }
        $.ajax({
            url: "/admin/userLevel/update",
            data: JSON.stringify({
                "id": primaryKey.toString(),
                "name": name,
                "requiredPoints": requiredPoints
            }),
            dataType: "json",
            type: "post",
            async: false,
            contentType: "application/json;charset=UTF-8",
            success: function (res) {
                if (res && res.errcode == 0) {
                    layer.msg(res.errmsg, {icon: 1, time: 1000}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.location.reload();
                        parent.layer.close(index);
                    });
                } else {
                    layer.msg("操作失败", {icon: 2, time: 1000});
                }
            },
            error: function (e) {
                layer.msg('网络异常,请稍后再试', new Function());
            }
        });
        return false;
    });

    function checkName(nameTmp, name) {
        var flag = false;
        /*console.log(nameTmp);
        console.log(name);*/
        if (name == null || name.length < 0 || name == nameTmp) {
            return true;
        }
        $.ajax({
            url: "/admin/userLevel/checkName",
            data: "name=" + name,
            type: "get",
            async: false,
            dataType: "json",
            success: function (res) {
                if (res && res.errcode == 0) {
                    layer.tips("该名称已存在，请勿重复添加", '#name', {tips: [3, 'red']});
                    $('#name').focus();
                } else {
                    flag = true;
                }
            },
            error: function (e) {
                layer.msg('网络异常！', new Function());
            }
        });
        return flag;
    };

    function checkPoints(pointTmp, points) {
        /*console.log(points);
        console.log(pointTmp);*/
        var flag = false;
        if(points != null && pointTmp == points)
            return true;
        if (points != null && points.length > 0) {
            $.ajax({
                url: "/admin/userLevel/checkRequiredPoints",
                data: "points=" + points,
                type: "get",
                async: false,
                dataType: "json",
                success: function (res) {
                    if (res && res.errcode == 0) {
                        layer.tips("该会员积分已存在，请勿重复添加", '#requiredPoints', {tips: [3, 'red']});
                        $('requiredPoints').focus();
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