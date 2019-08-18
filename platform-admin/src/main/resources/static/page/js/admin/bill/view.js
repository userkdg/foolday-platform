function convertImg(imgUrl, pixel) {
    var imgLable = '<img class="layui-upload-img" src="'+ imgUrl +'" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
    return imgLable;
};

function convertStatus(status) {
    var statusDesc = "";
    if (status == true) {
        statusDesc = "已退款";
    } else {
        statusDesc = "已支付";
    }
    return statusDesc;
};

function convertType(status) {
    var statusDesc = "";
    if (status == "0") {
        statusDesc = "酒店预订";
    } else {
        statusDesc = "套餐购买";
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
    var tableIns;


    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/bill/findAll',
        title: '订单列表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'username', title: '会员名', align: 'center'},
                {field: 'phone', title: '手机号码', align: 'center'},
                {field: 'create_time', title: '下单时间', align: 'center',
                    templet: '<div>{{dateFormat(d.create_time, "yyyy-MM-dd hh:mm")}}</div>'},
                {field: 'price', title: '金额', align: 'center'},
                {
                    field: 'billType', title: '账单类型', align: 'center', color:'&#xe654;',
                    templet: '<div>{{convertType(d.billType)}}</div>'
                },
                {field: 'refund', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.refund)}}</div>'},
                {field: 'order_number', title: '账单号', align: 'center'},
                {field: 'remarks', title: '备注', align: 'center'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width:"20%"}
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

    form.on("submit(query)", function (data) {
        var type = $("#type").val();
        var value = $("#value").val();
        var url;
        if (type == "0")
            url = "/admin/bill/findAllByPhone?value="+value;
        else if(type == "1")
            url = "/admin/bill/findAllByUsername?value="+value;
        else
            url = "/admin/bill/findByOrderNum?value="+value;

        tableIns = table.render({
            elem: '#dataView',
            url: url,
            title: '会员列表',
            method: 'get',
            toolbar: '#toolbar',
            page: true,
            limit: 5,
            limits: [5, 15, 25],
            cols: [
                [
                    {field: 'id', title: 'ID', align: 'center', sort: true},
                    {field: 'username', title: '会员名', align: 'center'},
                    {field: 'phone', title: '手机号码', align: 'center'},
                    {
                        field: 'create_time', title: '下单时间', align: 'center',
                        templet: '<div>{{dateFormat(d.create_time, "yyyy-MM-dd hh:mm")}}</div>'
                    },
                    {field: 'price', title: '金额', align: 'center', width: "8%"},
                    {
                        field: 'billType', title: '账单类型', align: 'center', color: '&#xe654;', width: "8%",
                        templet: '<div>{{convertType(d.billType)}}</div>'
                    },
                    {
                        field: 'refund', title: '状态', align: 'center', width: "8%",
                        templet: '<div>{{convertStatus(d.refund)}}</div>'
                    },
                    {field: 'order_number', title: '账单号', align: 'center', width: "20%"},
                    {field: 'remarks', title: '备注', align: 'center', width: "16%"},
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

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'enable':
                layer.confirm('确认已支付了吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/bill/pay",
                        data: "id=" + data.id,
                        type: "get",
                        dataType: "json",
                        success: function(result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error:function(e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case 'prohibit':
                layer.confirm('确认已退款了吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/bill/refund",
                        data: "id=" + data.id,
                        type: "get",
                        dataType: "json",
                        success: function(result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error:function(e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
        };
    });

    table.on('toolbar(data)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data; // 获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: "添加",
                    content: "/admin/bill/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
    });
});
