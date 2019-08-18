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

function convertRecommend(popular) {
    var statusDesc = "";
    if (popular == 1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">推荐</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">正常</button>';
    }
    return statusDesc;
};

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;


    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/hotel/view',
        title: '酒店列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {
                    field: 'scenicPicture', title: '景点图片', align: 'center',
                    templet: '<div>{{convertImg(d.scenicPicture, 65)}}</div>'
                },
                {field: 'name', title: '酒店名称', align: 'center'},
                {field: 'eName', title: '英文名称', align: 'center'},
                {field: 'province', title: '省份', align: 'center'},
                {field: 'city', title: '城市', align: 'center'},
                {field: 'address', title: '酒店地址', align: 'center'},
                {field: 'sketch', title: '酒店简述', align: 'center'},
                {field: 'food', title: '酒店美食', align: 'center'},
                {field: 'characteristic', title: '酒店特色', align: 'center'},
                {field: 'phone', title: '联系电话', align: 'center'},
                {field: 'sequence', title: '排序顺序', align: 'center'},
                {
                    field: 'status', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.status)}}</div>'
                },
                {
                    field: 'popular', title: '热门', align: 'center',
                    templet: '<div>{{convertRecommend(d.popular)}}</div>'
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

    form.on("submit(query)", function (data) {
        var province = data.field.province;
        var city = data.field.city;
        var name = data.field.name;
        var eName = data.field.eName;
        var popular = data.field.popular;
        // 表格重新加载
        tableIns.reload({
            where: {
                province: province,
                city: city,
                name: name,
                eName: eName,
                popular: popular
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
                    content: "/admin/hotel/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'pictureView':
                layer.open({
                    type: 2,
                    title: "查看",
                    content: "/admin/hotelPicture/view?hotelId=" + data.id,
                    resize: false,
                    area: ['1000px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/hotel/delete",
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
            case 'enable':
                layer.confirm('确认启用吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/hotel/enable",
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
                    content: "/admin/hotel/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
    });
});
