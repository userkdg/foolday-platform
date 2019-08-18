layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    var uploadInst1 = layui.upload.render({
        elem: '#showPicture1',
        url: '/file/upload',
        auto: false,
        size: 2048, // 限制文件大小，单位KB
        choose: function (obj) {
            obj.preview(function (index, file, result) {
                $('#showPicture1').attr('src', result); // 图片链接（base64）
            });
        },
        done: function (res) {
            if (res.errcode == 0) {
                $('#scenicPicture').val(res.data);
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
        uploadInst1.upload();
        uploadInst.upload();
        doSubmit();
        return false;
    });

    function doSubmit() {
        var data = {
            "username": $('#username').val(),
            "phone": $('#phone').val(),
            "photo": $('#photo').val(),
            "idType": $('#idType').val(),
            "identity": $('#identity').val(),
            "email": $('#email').val(),
            "address": $('#address').val(),
            "birthday": $('#birthday').val(),
            "gender": $('#gender').val(),
            "status": $('#status').val(),
            "password": $('#password').val()
        };
        var a = checkPone();
        if (!a) {
            return false;
        }
        a = checkEmail();
        if (!a) {
            return false;
        }
        a = checkIDType();
        if (!a) {
            return false;
        }
        layui.jquery.ajax({
            url: "/admin/userAdmin/addUser",
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
                url: "/api/checkPhone",
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

    function checkIDType() {
        var flag = false;
        var IDType = $("#IDType").val();
        var identity = $("#identity").val();
        if (IDType != null && IDType.length > 0 && identity != null && identity.length > 0) {
            $.ajax({
                url: "/api/checkId",
                data: "identity=" + identity + "&type=" + IDType,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 100) {
                        layer.tips("证件号错误", '#identity', {tips: [3, 'red']});
                        $('identity').focus();
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

    function checkEmail() {
        var flag = false;
        var email = $("#email").val();
        if (email != null && email.length > 0) {
            $.ajax({
                url: "/api/checkEmail",
                data: "email=" + email,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 100) {
                        layer.tips(result.errmsg, '#email', {tips: [3, 'red']});
                        $('email').focus();
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