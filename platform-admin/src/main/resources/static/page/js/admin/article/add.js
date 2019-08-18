layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var laydate = layui.laydate;

    function getAttribution(attributions, index) {
        var attributionArray = attributions.toString().split(";");
        return attributionArray[index];
    }

    function getTop() {
        var top = $("#top");
        console.log(top);
        if(top.is(':checked'))
            return "1";
        else
            return "0";
    }

    function init() {
        getInitData();
    }
    init();

    var initData; // 回显的数据
    function getInitData() {
        var typeId = $("#typeId").val();
        $.ajax({
            url: "/admin/ArticleType/getInfo",
            data: "id=" + typeId,
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    $("#label1").text(getAttribution(initData.attributionsName, 0));
                    $("#label2").text(getAttribution(initData.attributionsName, 1));
                    $("#label3").text(getAttribution(initData.attributionsName, 2));
                    if(result.superAdmin != true) {
                        $("#hotel").attr("style", "display:none");
                        $("#hotelId").removeAttr("lay-verify");
                    }
                    else {
                        defineSelect();
                    }
                    form.render(); // 重新绘制表单，让修改生效
                } else {
                    layer.msg('数据加载异常！', new Function());
                }
            },
            error:function(e) {
                layer.msg('网络异常！', new Function());
            }
        });
    }

    //设置内容，isAppendTo为TRUE是为追加内容
    var ue = UE.getEditor('content');
    function setContent(text) {
        UE.getEditor('content').execCommand('insertHtml', text)
    }

    function getContent() {
        return UE.getEditor('content').getContent();
    }


    laydate.render({
        elem: '#cancelTime'
        , type: 'datetime'
    });

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
                $('#cover').val(res.data);
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
            "typeId": $("#typeId").val(),
            "title": $('#title').val(),
            "cover": $('#cover').val(),
            "hotelId": $("#hotelId").val(),
            "characteristic": $('#characteristic').val(),
            "attributionsValue": $('#attribution1').val() + ";" + $('#attribution2').val() + ";" + $('#attribution3').val(),
            "introduction": $('#introduction').val(),
            "top": getTop(),
            "content": getContent(),
        };
        layui.jquery.ajax({
            url: "/admin/article/add",
            data: JSON.stringify(data),
            type: "post",
            dataType:"json",
            contentType: "application/json;charset=UTF-8",
            async: false,
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