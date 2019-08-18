layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;
    var primaryKey = $("#primaryKey").val();

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/integral/findById',
        title: '会员积分表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {field: 'id', title: 'ID', align: 'center', width: 80, sort: true},
                {field: 'integralType', title: '积分类型', align: 'center', width: 180},
                {field: 'createTime', title: '积分时间', align: 'center', width: 180,
                    templet: '<div>{{dateFormat(d.createTime, "yyyy-MM-dd hh:mm")}}</div>'},
                {field: 'integral', title: '积分数', align: 'center', width: 180}
            ]
        ],
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.errcode,
                "data": res.data,
                "count": res.count,
                "limit": 5
            }
        }
    });
});

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