layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;


    form.on("submit(add)", function (data) {
        doSubmit();
        return false;
    });

    function doSubmit() {
        var a = checkPone();
        if (!a) {
            return false;
        }
        a = checkOrderNumber();
        if(!a) {
            return false;
        }
        a = checkBill();
        if(!a) {
            return false;
        }
        var data = {
            "userId": $('#userId').val(),
            "billType": $('#billType').val(),
            "orderNumber": $('#orderNumber').val(),
            "price": $('#price').val(),
            "remarks": $('#remarks').val()
        };
        layui.jquery.ajax({
            url: "/admin/bill/add",
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

    function checkOrderNumber() {
        var flag = false;
        var orderNumber = $("#orderNumber").val();
        var userId = $("#userId").val();
        if (orderNumber != null && orderNumber.length > 0) {
            $.ajax({
                url: "/admin/bill/checkOrderNumber",
                data: "userId=" + userId + "&orderNumber=" + orderNumber,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 100) {
                        layer.tips(result.errmsg, '#orderNumber', {tips: [3, 'red']});
                        $('orderNumber').focus();
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

    function checkPone() {
        var flag = false;
        var phone = $("#phone").val();
        if (phone != null && phone.length > 0) {
            $.ajax({
                url: "/admin/bill/checkPhone",
                data: "phone=" + phone,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 100) {
                        layer.tips("不存在此手机号", '#phone', {tips: [3, 'red']});
                        $('phone').focus();
                    } else {
                        flag = true;
                        $("#userId").val(result.data);
                    }
                },
                error: function (e) {
                    layer.msg('网络异常！', new Function());
                }
            });
        } else {
            flag = false;
        }
        return flag;
    };

    function checkBill() {
        var flag = false;
        var orderNumber = $("#orderNumber").val();
        if (orderNumber != null && orderNumber.length > 0) {
            $.ajax({
                url: "/admin/bill/checkBill",
                data: "orderNumber=" + orderNumber,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 0) {
                        layer.tips("此订单号对应的账单已存在", '#orderNumber', {tips: [3, 'red']});
                        $('orderNumber').focus();
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