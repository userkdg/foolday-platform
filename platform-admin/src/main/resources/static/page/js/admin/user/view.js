function convertImg(imgUrl, pixel) {
    var imgLable = '<img class="layui-upload-img" src="'+ imgUrl +'" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
    return imgLable;
};

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
    var tableIns;


    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/userAdmin/findAll',
        title: '会员列表',
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
                {field: 'idType', title: '证件类型', align: 'center',
                    templet: '<div>{{convertIDType(d.idType)}}</div>'},
                {field: 'identity', title: '证件号', align: 'center'},
                {
                    field: 'status', title: '状态', align: 'center', color:'&#xe654;',
                    templet: '<div>{{convertStatus(d.status)}}</div>'
                },
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
        var url;
        var type = $("#type").val();
        var value = $("#value").val();
        if(type == "0")
            url = "/admin/userAdmin/findByPhone?value="+value;
        else
            url = "/admin/userAdmin/findByName?value="+value;
        // 表格重新加载
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
                    {field: 'id', title: 'ID', align: 'center', sort: true, width:"20%"},
                    {field: 'username', title: '会员名', align: 'center'},
                    {field: 'phone', title: '手机号码', align: 'center'},
                    {field: 'idType', title: '证件类型', align: 'center',
                        templet: '<div>{{convertIDType(d.idType)}}</div>'},
                    {field: 'identity', title: '证件号', align: 'center', width:"20%"},
                    {
                        field: 'status', title: '状态', align: 'center', color:'&#xe654;',
                        templet: '<div>{{convertStatus(d.status)}}</div>'
                    },
                    {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width:"20%"}
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
            case 'pictureView':
                layer.open({
                    type: 2,
                    title: "查看",
                    content: "/admin/userAdmin/view?id=" + data.id,
                    resize: false,
                    area: ['600px', '850px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/userAdmin/delUser",
                        data:"id=" + data.id,
                        type: "get",
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
            case 'enable':
                layer.confirm('确认激活吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/userAdmin/activateUser",
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
                layer.confirm('确认冻结吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/userAdmin/prohibit",
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
                    content: "/admin/userAdmin/add",
                    resize: false,
                    area: ['600px', '800px']
                });
                break;
        }
    });
});
