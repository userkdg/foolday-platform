
function convertImg(imgUrl, pixel) {
    var imgLable = '<img class="layui-upload-img" src="' + imgUrl + '" style="width:' + pixel + 'px; height:' + pixel + 'px;">';
    return imgLable;
};

function convertStatus(status) {
    var statusDesc = "";
    if (status == 1) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">满房</button>';
    } else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">空房</button>';
    }
    return statusDesc;
};

function convertRecommend(popular) {
    var statusDesc = "";
    if (popular == 0) {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">不包含</button>';
    }  else {
        statusDesc = '<button class="layui-btn layui-btn-radius layui-btn-xs">包含</button>';
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
                        $('#hotelId').append(new Option(item.name,item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    defineSelect();


    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/house/view',
        title: '房型列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field : 'picture', title : '封面图片', align: 'center',
                    templet: '<div>{{convertImg(d.picture, 70)}}</div>'
                },
                {field: 'name', title: '房型名称', align: 'center'},
                {field: 'area', title: '房型面积', align: 'center'},
                {field: 'bedType', title: '床型', align: 'center'},
                {field: 'price', title: '价格', align: 'center'},
                {field: 'residence', title: '可住人数', align: 'center'},
                {field: 'content', title: '包含内容', align: 'center'},
                {
                    field: 'isBreakfast', title: '早餐', align: 'center',
                    templet: '<div>{{convertRecommend(d.isBreakfast)}}</div>'
                },
                {
                    field: 'isAirport', title: '接机', align: 'center',
                    templet: '<div>{{convertRecommend(d.isAirport)}}</div>'
                },
                {
                    field: 'isCancelTime', title: '取消', align: 'center',
                    templet: '<div>{{convertRecommend(d.isCancelTime)}}</div>'
                },
                {
                    field: 'status', title: '状态', align: 'center',
                    templet: '<div>{{convertStatus(d.status)}}</div>'
                },
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item',width:"18%"}
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
        var price = data.field.price;
        var residence = data.field.residence;
        var isAirport = data.field.isAirport;
        var isBreakfast = data.field.isBreakfast;
        // 表格重新加载
        tableIns.reload({
            where: {
                isBreakfast: isBreakfast,
                isAirport: isAirport,
                residence: residence,
                price: price
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
                    content: "/admin/house/edit?id=" + data.id,
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/house/delete",
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
                    content: "/admin/house/add",
                    resize: false,
                    area: ['600px', '600px']
                });
                break;
        }
        ;
    });
});