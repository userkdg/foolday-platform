layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/ArticleType/findAll',
        title: '资讯类型列表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'name', title: '名称', align: 'center'},
                {field: 'englishTitle', title: '英文名称', align: 'center'},
                {field: 'createTime', title: '创建时间', align: 'center',
                    templet: '<div>{{dateFormat(d.createTime, "yyyy-MM-dd hh:mm")}}</div>'},
                {field: 'attributionsName', title: '资讯特性', align: 'center'},
                {title: '操作', fixed: "right", align: 'left', toolbar: '#operator_item', width: "20%"}
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

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'addArticle':
               /* $("#J_iframe").attr('src',"/admin/article/add");*/
                layer.open({
                    type: 2,
                    title: "添加新资讯",
                    content: "/admin/ArticleType/addArticle?id=" + data.id,
                    resize: false,
                    area: ['1280px', '800px']
                });
                break;
            case 'edit':
                layer.open({
                    type: 2,
                    title: "编辑资讯",
                    content: "/admin/ArticleType/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/ArticleType/delete",
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
                    title: "添加",
                    content: "/admin/ArticleType/add",
                    resize: false,
                    area: ['550px', '600px']
                });
                break;
        }
        ;
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