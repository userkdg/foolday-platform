layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;


    function defineSelectRole() {
        $.ajax({
            url: '/admin/sysRole/findAll',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0){
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#sysRoleId').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }
    function defineSelectHotel() {
        $.ajax({
            url: '/admin/hotel/getHotelName',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#hotelId').append(new Option(item.name,item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    defineSelectRole();
    defineSelectHotel();

    form.on("submit(add)", function (data) {
        doSubmit();
        return false;
    });

    function doSubmit() {
        var data = {
            "username": $('#username').val(),
            "phone": $('#phone').val(),
            "password": $('#password').val(),
            "remarks": $('#remarks').val(),
            "state": $('#status').val(),
            "hotelId": $('#hotelId').val(),
            "sysRoleId": $('#sysRoleId').val(),
        };
        var a = checkPone();
        if (!a) {
            return false;
        }
        layui.jquery.ajax({
            url: "/admin/sysAdmin/addAdmin",
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
    }

    function checkPone() {
        var flag = false;
        var phone = $("#phone").val();
        if (phone != null && phone.length > 0) {
            $.ajax({
                url: "/admin/checkPhone",
                data: "phone=" + phone,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 100) {
                        layer.tips(result.errmsg, '#phone', {tips: [3, 'red']});
                        $('phone').focus();
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