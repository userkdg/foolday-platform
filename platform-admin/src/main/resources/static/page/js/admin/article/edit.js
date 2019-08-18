layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var laydate = layui.laydate;

    //设置内容，isAppendTo为TRUE是为追加内容
    var ue = UE.getEditor('content');

    function getAttribution(attributions, index) {
        var attributionArray = attributions.toString().split(";");
        return attributionArray[index];
    }

    function setTop(top) {
        if(top == "1") {
            $("#top").prop("checked",true);
        }
        else {
            $("#top").prop("checked",false);
        }
    }

    function getTop() {
        var top = $("#top");
        if(top.prop('checked'))
            return "1";
        else
            return "0";
    }

    function init() {
        getInitData1();
        getInitData2();
    }
    init();

    function setContent(text) {
        UE.getEditor('content').ready(function () {
            UE.getEditor('content').execCommand('insertHtml', text)
        });
    }

    function getContent() {
        return UE.getEditor('content').getContent();
    }

    laydate.render({
        elem: '#cancelTime'
        , type: 'datetime'
    });

    var initData; // 回显的数据
    function getInitData1() {
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

    function getInitData2() {
        var id = $("#id").val();
        $.ajax({
            url: "/admin/article/findById",
            data: "id=" + id,
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    $("#title").val(initData.title);
                    $("#introduction").val(initData.introduction);
                    $("#characteristic").val(initData.characteristic);
                    $("#showPicture").attr("src", initData.cover);
                    $("#attribution1").val(getAttribution(initData.attributionsValue, 0));
                    $("#attribution2").val(getAttribution(initData.attributionsValue, 1));
                    $("#attribution3").val(getAttribution(initData.attributionsValue, 2));
                    setTop(initData.top);
                    setContent(initData.content);
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

    form.on("submit(edit)", function (data) {
        var cover = $('#cover').val();
        if (cover == null || cover.length == 0) {
            uploadInst.upload();
            return false;
        } else {
            doSubmit();
            return false;
        }
    });


    function doSubmit() {
        var data = {
            "id": $("#id").val(),
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
            url: "/admin/article/update",
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