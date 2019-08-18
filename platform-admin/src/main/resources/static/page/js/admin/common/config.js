/**
 * 定义layui组件
 */
var $ = layui.jquery,
	form = layui.form, 
	table = layui.table, 
	layer = layui.layer,
	upload = layui.upload;

/**
 * 日期格式化
 * @param datetime Date类型时间
 * @param format 需要转换成的格式
 * @returns
 */
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

function convertGender(gender) {
	var genderDesc = "";
	if (gender == 1) {
		genderDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">男性</button>';
	} else if (gender == 2) {
		genderDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">女性</button>';
	} else {
		genderDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm">保密</button>';
	}
   	return genderDesc;
};

function convertType(type) {
	var typeDesc = "";
	if (type == 1) {
		typeDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">购买</button>';
	} else if (type == 2) {
		typeDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">回收</button>';
	}
	return typeDesc;
};

function convertStatus(status) {
	var statusDesc = "";
	if (status == -1) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">删除</button>';
	} else if (status == 0) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm">禁用</button>';
	} else {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">正常</button>';
	}
   	return statusDesc;
};

function convertPayType(payType) {
	var statusDesc = "";
	if (payType == 0) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary">未支付</button>';
	} else if (payType == 1) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-normal">支付宝支付</button>';
	} else if (payType == 2) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">微信支付</button>';
	}
   	return statusDesc;
};

function convertPayStatus(payStatus) {
	var statusDesc = "";
	if (payStatus == -1) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary">已退款</button>';
	} else if (payStatus == 0) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm">未付款</button>';
	} else if (payStatus == 1) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">已付款</button>';
	}
   	return statusDesc;
};

function convertOrderStatus(orderStatus) {
	var statusDesc = "";
	if (orderStatus == -1) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary">已关闭</button>';
	} else if (orderStatus == 0) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">未完成</button>';
	} else if (orderStatus == 1) {
		statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">已完成</button>';
	}
   	return statusDesc;
};

function convertImg(imgUrl, pixel) {
	var imgLable = '<img class="layui-upload-img" src="'+ imgUrl +'" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
   	return imgLable;
};

/**
 * 判断是否为空
 * @param str 字符串
 * @returns
 */
function isBlank(str) {
    if (typeof str === 'undefined' || str === null || $.trim(str) === '') {
        return true;
    }
    return false;
};