function convertStatus(status) {
    var statusDesc = "";
    if (status == "NORMAL") {
        statusDesc = "正常";
    } else if (status == "FROZEN") {
        statusDesc = "冻结";
    } else {
        statusDesc = "删除";
    }
    return statusDesc;
};

function convertIDType(idType) {
    var statusDesc = "";
    if (idType == "ID_CARD") {
        statusDesc = "大陆身份证";
    } else {
        statusDesc = "其他证件类型";
    }
    return statusDesc;
};

function convertGender(gender) {
    var statusDesc = "";
    if (gender == "MALE") {
        statusDesc = "男";
    } else if(gender == "FEMALE" ){
        statusDesc = "女";
    } else {
        statusDesc = "不详"
    }
    return statusDesc;
};

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
            url: "/admin/userAdmin/find",
            data: "id=" + primaryKey,
            type: "get",
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    initData = result.user;
                    $("#id").val(initData.id);
                    $("#username").val(initData.username);
                    $('#showPicture').attr('src',initData.photo);
                    $("#address").val(initData.address);
                    $('#email').val(initData.email);
                    $("#phone").val(initData.phone);
                    $("#identity").val(initData.identity);
                    $("#idType").val(convertIDType(initData.idType));
                    $("#birthday").val(initData.birthday);
                    $("#totalIntegral").val(initData.totalIntegral);
                    $("#creatTime").val(dateFormat(initData.creatTime,"yyyy-MM-dd"));
                    $("#gender").val(convertGender(initData.gender));
                    $("#status").val(convertStatus(initData.status));
                    $("#userLevel").val(result.userLevel.name);
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


