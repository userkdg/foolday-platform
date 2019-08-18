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

function convertImg(imgUrl, pixel) {
    var imgLable = '<img class="layui-upload-img" src="' + imgUrl + '" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
    return imgLable;
};


function convertStatus(status) {
    var statusDesc = "";
    if (status == 1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">冻结</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">正常</button>';
    }
    return statusDesc;
};

function convertType(type) {
    var statusDesc = "";
    if (type == 0) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">首页</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">banner</button>';
    }
    return statusDesc;
};

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var tableIns;


    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/indexSlideshow/view',
        title: '图片列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'title', title: '标题', align: 'center'},
                {field: 'hotelName', title: '酒店名称', align: 'center'},
                {field: 'description', title: '描述', align: 'center'},
                {field: 'sequence', title: '排序序号', align: 'center'},
                {
                    field: 'picture', title: '图片', align: 'center',
                    templet: '<div>{{convertImg(d.picture, 80)}}</div>'
                },
                {
                    field: 'status', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.status)}}</div>'
                },
                {
                    field: 'type', title: '类型', align: 'center',
                    templet: '<div>{{convertType(d.type)}}</div>'
                },
                {
                    field: 'createTime', title: '创建时间', align: 'center',
                    templet: '<div>{{dateFormat(d.createTime, "yyyy-MM-dd hh:mm")}}</div>'
                },
                {
                    field: 'updateTime', title: '更新时间', align: 'center',
                    templet: '<div>{{dateFormat(d.updateTime, "yyyy-MM-dd hh:mm")}}</div>'
                },
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: '25%'}
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
        // 表格重新加载
        tableIns.reload({
            where: {}
        });
        return false;
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'edit':
                layer.open({
                    type: 2,
                    title: "编辑",
                    content: "/admin/indexSlideshow/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/indexSlideshow/delete",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (res) {
                            if (!res || res.errcode != 0) {
                                layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
                                return;
                            }
                            location.reload();
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case 'unable':
                layer.confirm('确认冻结吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/indexSlideshow/unable",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (res) {
                            if (!res || res.errcode != 0) {
                                layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
                                return;
                            }
                            location.reload();
                        },
                        error: function (e) {
                            layer.msg('网络异常,请稍后再试', new Function());
                        }
                    });
                });
                break;
            case 'enable':
                layer.confirm('确认启用吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/indexSlideshow/enable",
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (res) {
                            if (!res || res.errcode != 0) {
                                layer.msg(res ? res.errmsg : "请求出错!", {icon: 2, time: 10000});
                                return;
                            }
                            location.reload();
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
                    content: "/admin/indexSlideshow/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
        ;
    });
});