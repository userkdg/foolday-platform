layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;

    function defineSelect() {
        var hotelId = $('#hotelId').val();
        var houseId = $('#houseId').val();
        $.ajax({
            url: '/admin/room/getRoomName',
            dataType: 'json',
            type: 'get',
            data: {
                houseId: houseId,
                hotelId: hotelId
            },
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#roomId').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    defineSelect();

    form.on("submit(add)", function (data) {
        var data = {
            "roomId": $('#roomId').val(),
            "userName": $('#userName').val(),
        };
        $.ajax({
            url: "/admin/reserveOrder/residence",
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