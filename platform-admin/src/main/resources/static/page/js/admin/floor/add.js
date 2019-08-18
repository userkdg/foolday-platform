layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    function defineSelect() {
        $.ajax({
            url: '/admin/hotel/getHotelName',
            dataType: 'json',
            type: 'get',
            success: function (res) {
                if (res.errcode == 0) {
                    var data = res.data;
                    $.each(data, function (index, item) {
                        $('#hotelId').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    defineSelect();

    form.on("submit(add)", function (data) {
        var data = {
            "name": data.field.name,
            "remarks": data.field.remarks,
            "hotelId": data.field.hotelId
        };
        var a = checkName();
        if (!a) {
            return false;
        }
        $.ajax({
            url: "/admin/floor/add",
            data: JSON.stringify(data),
            contentType: 'application/json',
            type: "post",
            async: false,
            dataType: "json",
            success: function (res) {
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
                    return;
                }
                parent.location.reload();
                parent.layer.close(parent.layer.getFrameIndex(window.name));
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
        if (name == null || name.length < 0) {
            flag = true;
        }
        $.ajax({
            url: "/admin/floor/checkName",
            data: {
                name: name
            },
            type: "get",
            async: false,
            dataType: "json",
            success: function (res) {
                if (res && res.errcode == 400) {
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
});