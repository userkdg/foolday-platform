layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    function defineSelect() {
        var hotelId = $("#hotelId").val();
        $.ajax({
            url: '/admin/house/getHouseName',
            dataType: 'json',
            type: 'get',
            data: {
                id: hotelId
            },
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#houseId').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    defineSelect();

    form.on("submit(add)", function (data) {
        var data = {
            "name": $('#name').val(),
            "houseId": $('#houseId').val(),
            "hotelId": $('#hotelId').val()
        };
        var a = checkName();
        if (!a) {
            return false;
        }
        $.ajax({
            url: "/admin/room/add",
            data: JSON.stringify(data),
            contentType: 'application/json',
            type: "post",
            async: false,
            dataType: "json",
            success: function (result) {
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
            error: function (e) {
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
                success: function (result) {
                    if (result && result.errcode == 400) {
                        layer.tips("该名称已存在，请勿重复添加", '#name', {tips: [3, 'red']});
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