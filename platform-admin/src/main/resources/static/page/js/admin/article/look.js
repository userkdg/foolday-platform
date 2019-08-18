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

    function setTop(top) {
        if(top == "1") {
            $("#top").prop("checked",true);
        }
        else {
            $("#top").prop("checked",false);
        }
    }

    function getHotelName(hotelId) {
        //console.log(hotelIdTmp);
        $.ajax({
            url: '/admin/hotel/getInfo',
            data: "id=" + hotelId,
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $("#hotelName").val(data.name);
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    function init() {
        getInitData1();
        getInitData2();
    }
    init();

    //设置内容，isAppendTo为TRUE是为追加内容
    var ue = UE.getEditor('content');

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
                    $("#cover").attr("src", initData.cover);
                    $("#characteristic").val(initData.characteristic);
                    $("#introduction").val(initData.introduction);
                    $("#title").val(initData.title);
                    $("#attribution1").val(getAttribution(initData.attributionsValue, 0));
                    $("#attribution2").val(getAttribution(initData.attributionsValue, 1));
                    $("#attribution3").val(getAttribution(initData.attributionsValue, 2));
                    setTop(initData.top);
                    getHotelName(initData.hotelId);
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
});