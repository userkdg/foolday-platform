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
            "M+": datetime.getMonth() + 1,                 	//月份
            "d+": datetime.getDate(),                    		//日
            "h+": datetime.getHours(),                   		//小时
            "m+": datetime.getMinutes(),                 		//分
            "s+": datetime.getSeconds(),                 		//秒
            "q+": Math.floor((datetime.getMonth() + 3) / 3), 	//季度
            "S": datetime.getMilliseconds()             		//毫秒
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
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
    function getInitData(id) {

        $.ajax({
            url: "/admin/resident/view",
            data: {
                orderId: id
            },
            type: "post",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    $("[name='id']").val(initData.id);
                    $("[name='orderId']").val(initData.orderId);
                    $("[name='residentsName']").val(initData.residentsName);
                    $("[name='residentsName']").val(initData.residentsName);
                    $("[name='phone']").val(initData.phone);
                    $("[name='email']").val(initData.email);
                    $("[name='adultNumber']").val(initData.adultNumber);
                    $("[name='childNumber']").val(initData.childNumber);
                    $("[name='identity']").val(initData.identity);
                    $("[name='arriveTime']").val(initData.arriveTime);
                    $("[name='residentTime']").val(dateFormat(initData.residentTime,"yyyy-MM-dd hh:mm"));
                    $("[name='exitTime']").val(dateFormat(initData.exitTime,"yyyy-MM-dd hh:mm"));
                    $("[name='remarks']").val(initData.remarks);
                    $("[name='status']").val(initData.status);

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