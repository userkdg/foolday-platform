layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

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


    var uploadInst = upload.render({
        elem: '#showPicture',
        url: '/file/upload',
        auto: false,
        size: 2048, // 限制文件大小，单位KB
        choose: function (obj) {
            obj.preview(function (index, file, result) {
                $('#showPicture').attr('src', result); // 图片链接（base64）
            });
        },
        done: function (res) {
            if (res.errcode == 0) {
                $('#picture').val(res.data);
                doSubmit();
            } else {
                return layer.msg('上传失败');
            }
        },
        error: function () {
            var showText = $('#showText');
            showText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            showText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });

    form.on("submit(add)", function (data) {
        uploadInst.upload();
        return false;
    });


    function doSubmit() {
        var data = {
            "hotelId": $('#hotelId').val(),
            "picture": $('#picture').val(),
            "sequence": $('#sequence').val()
        };
        layui.jquery.ajax({
            url: "/admin/hotelPicture/add",
            data: data,
            type: "post",
            async: false,
            dataType: 'json',
            success: function (result) {
                console.log(result);
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

});