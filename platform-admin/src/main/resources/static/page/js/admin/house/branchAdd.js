layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;


    //多图片上传
    var uploadInst = upload.render({
        elem: '#showPicture',
        url: '/file/upload',
        auto: false,
        multiple: true,
        size: 2048, // 限制文件大小，单位KB
        before: function(obj) {
            layer.msg('图片上传中...', {
                icon: 16,
                shade: 0,
                time: 5000
            })
        },
        choose: function (obj) {
            obj.preview(function (index, file, result) {
                $('.layui-upload-list').prepend('<img src="'+ result +'" alt="'+ file.name +'" class="preview-img">')
            });
        },
        done: function (res) {
            images.push(res.data)
            layer.close(layer.msg());//关闭上传提示窗口
            if (res.errcode == 0) {
                $('#picture').val(images);
                var uploadImg = $(".preview-img")
                if(images.length == uploadImg.length) {
                    doSubmit();
                }
            } else {
                return layer.msg(res.message);
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
        var isAirport = $('#isAirport').val();
        var isBreakfast = $('#isBreakfast').val();
        var isCancelTime = $('#isCancelTime').val();
        if (($("#isAirport").prop('checked') != true)) {
            isAirport = 0;
        }
        if (($("#isBreakfast").prop('checked') != true)) {
            isBreakfast = 0;
        }
        if (($("#isCancelTime").prop('checked') != true)) {
            isCancelTime = 0;
        }
        var data = {
            "hotelId": $('#hotelId').val(),
            "name": $('#name').val(),
            "picture": $('#picture').val(),
            "bedType": $('#bedType').val(),
            "area": $('#area').val(),
            "price": $('#price').val(),
            "content": $('#content').val(),
            "residence": $('#residence').val(),
            "isCancelTime": isCancelTime,
            "isAirport": isAirport,
            "isBreakfast": isBreakfast
        };
        var a = checkName();
        if (!a) {
            return false;
        }
        layui.jquery.ajax({
            url: "/admin/house/add",
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

    function checkName() {
        var flag = false;
        var name = $("#name").val();
        var hotelId = $('#hotelId').val();
        if (name != null && name.length > 0) {
            $.ajax({
                url: "/admin/house/checkName",
                data: {
                    name: name,
                    id: hotelId
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