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
    if (status == -1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">删除</button>';
    } else if (status == 1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm">禁用</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">正常</button>';
    }
    return statusDesc;
};


layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var tableIns;

    function defineSelect() {
        $.ajax({
            url: '/admin/hotel/getHotelName',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#hotelId').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    defineSelect();

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/hotelPicture/view',
        title: '图片列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {
                    field: 'picture', title: '封面图片', align: 'center',
                    templet: '<div>{{convertImg(d.picture, 80)}}</div>'
                },
                /*{
                    field: 'status', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.status)}}</div>'
                },*/
                {
                    field: 'createTime', title: '创建时间', align: 'center',
                    templet: '<div>{{dateFormat(d.createTime, "yyyy-MM-dd hh:mm")}}</div>'
                },
                {
                    field: 'updateTime', title: '更新时间', align: 'center',
                    templet: '<div>{{dateFormat(d.updateTime, "yyyy-MM-dd hh:mm")}}</div>'
                }
                , {
                field: 'statua', title: '状态', align: 'center', templet: function (d) {  //自定义显示内容
                    var strCheck = d.status == "0" ? "checked" : "";
                    return '<input type="checkbox" name="status" lay-filter="statusChange" lay-skin="switch" lay-text="启用|冻结" ' + strCheck + ' mid=' + d.id + '>';
                }
            }
                ,
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: "25%"}
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
        // 表格重新加载
        tableIns.reload({
            where: {}
        });
        return false;
    });

    form.on('switch(statusChange)', function (obj) {
        var status = '';//转为 0、1
        obj.elem.checked ? status = '0' : status = '1';
        var id = $(this).attr('mid');
        $.ajax({
            url: "/admin/hotelPicture/change",
            data: {
                id: id,
                status: status
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
        return false;
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'edit':
                layer.open({
                    type: 2,
                    title: "编辑",
                    content: "/admin/hotelPicture/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/hotelPicture/delete",
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
            case 'picture':
                layer.confirm('确认设为封面吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/hotelPicture/picture",
                        data: {
                            hotelId: data.hotelId,
                            picture: data.picture
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
                    content: "/admin/hotelPicture/add?hotelId=" + data.hotelId,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
        ;
    });
});