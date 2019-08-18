function convertStatus(status) {
    var statusDesc = "";
    if (status == 1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">使用中</button>';
    } else if (status == -1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">已删除</button>';
    } else if (status == 2) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">已完结</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">未使用</button>';
    }
    return statusDesc;
};

function convertRecommend(popular) {
    var statusDesc = "";
    if (popular == 1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">微信</button>';
    } else if (popular == 0) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs  layui-btn-normal">支付宝</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">无</button>';
    }
    return statusDesc;
};

function convertPayStatus(payStuats) {
    var statusDesc = "";
    if (payStuats == 0) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm">未支付</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs ">已支付</button>';
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
    var tableIns;


    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/reserveOrder/view',
        title: '订单列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'orderNumber', title: '订单号', align: 'center'},
                {field: 'userName', title: '用户名', align: 'center'},
                {field: 'roomName', title: '房型名称', align: 'center'},
                {field: 'totalPirce', title: '总价', align: 'center'},
                {field: 'price', title: '价格', align: 'center'},
                {field: 'payNumber', title: '支付订单号', align: 'center'},
                {
                    field: 'payType', title: '支付类型', align: 'center',
                    templet: '<div>{{convertRecommend(d.payType)}}</div>'
                },
                {
                    field: 'payStatus', title: '支付状态', align: 'center',
                    templet: '<div>{{convertPayStatus(d.payStatus)}}</div>'
                },
                {field: 'payTime', title: '支付时间', align: 'center'},
                {
                    field: 'status', title: '订单状态', align: 'center',
                    templet: '<div>{{convertStatus(d.status)}}</div>'
                },
                {
                    field: 'createTime', title: '创建时间', align: 'center',
                    templet: '<div>{{dateFormat(d.createTime, "yyyy-MM-dd hh:mm")}}</div>'
                },
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: "30%"}
            ]
        ],
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.errmsg, {icon: 2, time: 5000}, function () {
                });
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
        var userName = data.field.userName;
        var status = data.field.status;
        var payStatus = data.field.payStatus;
        var payType = data.field.payType;
        // 表格重新加载
        tableIns.reload({
            where: {
                userName: userName,
                status: status,
                payStatus: payStatus,
                payType: payType
            }
        });
        return false;
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'check':
                layer.open({
                    type: 2,
                    title: "查看",
                    content: "/admin/resident/view?orderId=" + data.orderNumber,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/reserveOrder/delete",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case 'residence':
                layer.confirm('确实入住吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/reserveOrder/residence",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case "exit":
                layer.confirm('确定退房吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/reserveOrder/exit",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (result) {
                            if (result && result.errcode == 0) {
                                layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                    tableIns.reload();
                                });
                            } else {
                                layer.msg(result.errmsg, {icon: 2, time: 1000});
                            }
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
        }
        ;
    });

    table.on('toolbar(data)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data; // 获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: "添加",
                    content: "/admin/reserveOrder/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
        ;
    });
});