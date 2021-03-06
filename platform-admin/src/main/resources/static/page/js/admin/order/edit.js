layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var nameTemp;


    var primaryKey = $("#primaryKey").val();
    $.ajax({
        url: "/admin/hotelArea/getInfo",
        data: {
            id: primaryKey
        },
        type: "get",
        dataType: "json",
        success: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
                return;
            }
            nameTemp = res.data.city;
            form.val('hotelArea_info', {
                id: res.data.id,
                province: res.data.province,
                city: res.data.city,
                sequence: res.data.sequence
            });
        },
        error: function (res) {
            layer.msg('网络异常！', new Function());
        }
    });

    form.on("submit(edit)", function (data) {
        var province = data.field.province;
        var city = data.field.city;
        var sequence = data.field.sequence;
        if (city != nameTemp) {

        }
        $.ajax({
            url: "/admin/hotelArea/edit",
            data: {
                id: primaryKey,
                province: province,
                city: city,
                sequence: sequence
            },
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


});