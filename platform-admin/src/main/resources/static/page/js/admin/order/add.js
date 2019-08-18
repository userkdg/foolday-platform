layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;

    function defineSelect() {
        $.ajax({
            url: '/admin/hotel/getHotelName',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#hotelId').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }
    defineSelect();

    form.on('select(house)', function (data) {
        var hotelId = $("#hotelId").val();
        $.ajax({
            url: '/api/getHouse',
            dataType: 'json',
            type: 'post',
            data: {
                id: hotelId
            },
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $('#houseId').empty();
                    $.each(data, function (index, item) {
                        $('#houseId').append(new Option(item.name,item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    })


    laydate.render({
        elem: '#residentTime'
    });

    laydate.render({
        elem: '#exitTime'
    });

    form.on("submit(add)", function (data) {
        var  data = {
            "hotelId": $('#hotelId').val(),
            "houseId": $('#houseId').val(),
            "remarks": $('#remarks').val(),
            "residentTime": $('#residentTime').val(),
            "exitTime": $('#exitTime').val(),
            "residentsName": $('#residentsName').val(),
            "phone": $('#phone').val(),
            "email": $('#email').val(),
            "arriveTime": $('#arriveTime').val(),
            "adultNumber": $('#adultNumber').val(),
            "childNumber": $('#childNumber').val(),
            "identity": $('#identity').val(),
            "remarks": $('#remarks').val()
        };
        $.ajax({
            url: "/admin/reserveOrder/add",
            data: data,
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
                    layer.msg(result.errmsg, {icon: 2, time: 1000});
                }
            },
            error: function (e) {
                layer.msg('网络异常,请稍后再试', new Function());
            }
        });
        return false;
    });

});