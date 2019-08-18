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
    if (status == "0") {
        statusDesc = "否";
    } else {
        statusDesc = "是";
    }
    return statusDesc;
};

function convertImg(imgUrl, pixel) {
    var imgLable = '<img class="layui-upload-img" src="' + imgUrl + '" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
    return imgLable;
};

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;
    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/article/findAll',
        title: '资讯列表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'title', title: '资讯标题', align: 'center'},
                {
                    field: 'createDate', title: '发布时间', align: 'center',
                    templet: '<div>{{dateFormat(d.createDate, "yyyy-MM-dd hh:mm")}}</div>'
                },
                {field: 'typeName', title: '资讯类型', align: 'center'},
                {field: 'characteristic', title: '标签', align: 'center'},
                {
                    field: 'cover', title: '封面', align: 'center',
                    templet: '<div>{{convertImg(d.cover, 80)}}</div>'
                },
                {field: 'access', title: '访问量', align: 'center'},
                {field: 'hotelName', title: '发布酒店名', align: 'center'},
                {
                    field: 'top', title: '是否置顶', align: 'center',
                    templet: '<div>{{convertStatus(d.top)}}</div>'
                },
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: "20%"}
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

    function defineSelect() {
        $.ajax({
            url: '/admin/article/findType',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#type').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    defineSelect();

    form.on("submit(query)", function (data) {
        var type = $("#type").val();
        // 表格重新加载
        // 表格重新加载
        tableIns = table.render({
            elem: '#dataView',
            url: "/admin/article/findAllByType?type=" + type,
            title: '资讯列表',
            method: 'get',
            toolbar: '#toolbar',
            page: true,
            limit: 5,
            limits: [5, 15, 25],
            cols: [
                [
                    {field: 'id', title: 'ID', align: 'center', sort: true, width: "8%"},
                    {field: 'title', title: '资讯标题', align: 'center', width: "15%"},
                    {
                        field: 'createDate', title: '发布时间', align: 'center', width: "10%",
                        templet: '<div>{{dateFormat(d.createDate, "yyyy-MM-dd hh:mm")}}</div>'
                    },
                    {field: 'typeName', title: '资讯类型', align: 'center', width: "10%"},
                    {field: 'characteristic', title: '标签', align: 'center', width: "10%"},
                    {
                        field: 'cover', title: '封面', align: 'center', width: "10%",
                        templet: '<div>{{convertImg(d.cover, 80)}}</div>'
                    },
                    {field: 'access', title: '访问量', align: 'center', width: "8%"},
                    {field: 'hotelName', title: '发布酒店名', align: 'center', width: "10%"},
                    {
                        field: 'top', title: '是否置顶', align: 'center', width: "6%",
                        templet: '<div>{{convertStatus(d.top)}}</div>'
                    },
                    {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: "13%"}
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
        return false;
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "look":
                layer.open({
                    type: 2,
                    title: "查看",
                    content: "/admin/article/look?id=" + data.id,
                    resize: false,
                    area: ['1280px', '800px']
                });
                break;
            case 'edit':
                layer.open({
                    type: 2,
                    title: "修改",
                    content: "/admin/article/edit?id=" + data.id,
                    resize: false,
                    area: ['1280px', '800px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？删除后不可再更改!', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/article/delete",
                        data: "id=" + data.id,
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
                    title: "添加新资讯",
                    content: "/admin/article/add",
                    resize: false,
                    area: ['1280px', '800px']
                });
                break;
        }
    });

    /*table.on('toolbar(data)', function (obj) {

        switch (obj.event) {
            case 'addArticle':
                /!* $("#J_iframe").attr('src',"/admin/article/add");*!/
                layer.open({
                    type: 2,
                    title: "添加新资讯",
                    content: "/admin/ArticleType/addArticle?id=" + id,
                    resize: false,
                    area: ['1280px', '800px']
                });
                break;
        }
    });*/
});
