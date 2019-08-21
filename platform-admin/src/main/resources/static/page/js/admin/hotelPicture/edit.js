layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var pictrueTemp;

    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/hotelPicture/getInfo",
            data: {
                id: primaryKey
            },
            type: "get",
            dataType: "json",
            success: function (result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    pictrueTemp = initData.picture;
                    $("[name='id']").val(initData.id);
                    $("[name='sequence']").val(initData.sequence);
                    $("[name='picture']").val(initData.picture);
                    $('#showPicture').attr('src', initData.picture);
                    form.render(); // 重新绘制表单，让修改生效
                } else {
                    layer.msg('数据加载异常！', new Function());
                }
            },
            error: function (e) {
                layer.msg('网络异常！', new Function());
            }
        });
    }

    getInitData();

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


    form.on("submit(edit)", function (data) {
        var picture = $('#picture').val();
        if (picture != pictrueTemp || picture == null) {
            uploadInst.upload();
            return false;
        } else {
            doSubmit();
            return false;
        }
    });

    function doSubmit() {
        var picture = $('#picture').val();
        var id = $('#primaryKey').val();
        var sequence = $('#sequence').val();
        $.ajax({
            url: "/admin/hotelPicture/edit",
            data: {
                id: id,
                picture: picture,
                sequence: sequence
            },
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

});