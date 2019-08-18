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

function convertStatus(status) {
    var statusDesc = "";
    if (status == true) {
        statusDesc = "已查看";
    } else {
        statusDesc = "未查看";
    }
    return statusDesc;
};

/*function convertImg(imgUrl, pixel) {
    var imgLable = '<img class="layui-upload-img" src="' + imgUrl + '" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
    return imgLable;
};*/

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/feedback/findAll',
        title: '会员反馈',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {field: 'id', title: 'ID', align: 'center', width: '8%', sort: true},
                {field: 'username', title: '会员名', align: 'center', width: '10%'},
                {field: 'phone', title: '手机号', align: 'center', width: '10%'},
                {
                    field: 'create_date', title: '反馈时间', align: 'center', width: '10%',
                    templet: '<div>{{dateFormat(d.create_date, "yyyy-MM-dd hh:mm")}}</div>'
                },
                {field: 'view', title: '状态', align: 'center', width:'8%',
                    templet: '<div>{{convertStatus(d.view)}}</div>'},
                {
                    field: 'picture', title: '图片', align: 'center', width: '15%',
                    templet: '#showScreenhost'
                },
                {field: 'details', title: '内容详情', align: 'center', width: '24%'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: '14%'}
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
        var url;
        var type = $("#type").val();
        var value = $("#value").val();
        if(type == "0")
            url = "/admin/feedback/findAllByPhone?value="+value;
        else
            url = "/admin/feedback/findAllByUsername?value="+value;
        // 表格重新加载
        tableIns.reload({
            elem: '#dataView',
            url: url,
            title: '会员反馈',
            method: 'get',
            toolbar: '#toolbar',
            page: true,
            limit: 5,
            limits: [5, 15, 25],
            cols: [
                [
                    {field: 'id', title: 'ID', align: 'center', width: '8%', sort: true},
                    {field: 'username', title: '会员名', align: 'center', width: '10%'},
                    {field: 'phone', title: '手机号', align: 'center', width: '10%'},
                    {
                        field: 'create_date', title: '反馈时间', align: 'center', width: '10%',
                        templet: '<div>{{dateFormat(d.create_date, "yyyy-MM-dd hh:mm")}}</div>'
                    },
                    {field: 'view', title: '状态', align: 'center', width:'8%',
                        templet: '<div>{{convertStatus(d.view)}}</div>'},
                    {
                        field: 'picture', title: '图片', align: 'center', width: '15%',
                        templet: '#showScreenhost'
                    },
                    {field: 'details', title: '内容详情', align: 'center', width: '25%'},
                    {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: '14%'}
                ]
            ],
            parseData: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.errmsg);
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
        return false;
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/feedback/delete",
                        data: 'id=' + data.id,
                        type: "get",
                        dataType:"json",
                        success: function (result) {
                            layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                                tableIns.reload();
                            });
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case 'pictureView':
                layer.open({
                    type: 2,
                    title: "查看",
                    content: "/admin/feedback/check?id=" + data.id,
                    resize: false,
                    area: ['600px', '700px'],
                    end: function () {
                        tableIns.reload();
                    }
                });
                break;
        };
    });

});
