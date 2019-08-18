function convertImg(picture) {
    var result = ""
    var srr=picture.split(";");
    for(var j in srr) {
        result = result + "<div style='margin:10px 10px; display:inline-block !important;'>" +
            "<img style = 'width:120px; height:120px;display:inline-block' src = " + srr[j] + " /></div>"
    }
    return result;
}

function dateFormat(datetime, format) {
    if (datetime != null && datetime != "") {
        if (parseInt(datetime) == datetime) {
            if (datetime.length == 10) {
                datetime = parseInt(datetime) * 1000;
            } else if (datetime.length == 13) {
                datetime = parseInt(datetime);
            }
        }
        datetime = new Date(datetime);
        var o = {
            "M+" : datetime.getMonth() + 1,                 	//月份
            "d+" : datetime.getDate(),                    		//日
            "h+" : datetime.getHours(),                   		//小时
            "m+" : datetime.getMinutes(),                 		//分
            "s+" : datetime.getSeconds(),                 		//秒
            "q+" : Math.floor((datetime.getMonth() + 3) / 3), 	//季度
            "S"  : datetime.getMilliseconds()             		//毫秒
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("("+ k +")").test(format)) {
                format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
    } else {
        format = "";
    }

    return format;
};


layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    function init() {
        getInitData();
    }
    init();

    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/feedback/view",
            data: "id=" + primaryKey,
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    $("#id").val(initData.id);
                    $("#username").val(initData.username);
                    $("#phone").val(initData.phone);
                    $("#create_date").val(dateFormat(initData.createDate,"yyyy-MM-dd"));
                    $("#picture").html(convertImg(initData.picture));
                    $("#details").val(initData.details);
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


