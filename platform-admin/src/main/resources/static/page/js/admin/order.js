var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
        specs: [],
        stock: 0,
        price: 0,
        checked: '',
        id: '',
        logoImg: "",
        detailImg: "",
        pre_imgs: false,
        imgs: [],
    },
    methods: {
        delSpec: function (index) {
            this.specs.splice(index, 1);
        },
        clear_path: function (index) {
            if (index == 1) {
                this.detailImg = "";
                this.detail_result = "未上传!";
            } else if (index == 2) {
                this.logoImg = "";
                this.logo_result = "未上传!";
            } else if (index == 3) {
                this.pre_imgs = false;
                this.imgs = [];
                this.upload_result = "未上传";
            }
        },
        formatDate: function (datetime, pattern) {
            var date = new Date();
            date.setTime(datetime);
            var year = date.getFullYear();
            var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
            var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            var hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
            var minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
            var second = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
            return pattern.replace(/YYYY/igm, year)
                .replace(/MM/gm, month)
                .replace(/DD/igm, day)
                .replace(/HH/igm, hour)
                .replace(/mm/gm, minute)
                .replace(/ss/gm, second);
        }
    }
})

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var layedit = layui.layedit;


    $("#order_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }

        data.logo = vm.detailImg;
        data.pics = vm.imgs.join(",");
        data.thumb = vm.logoImg;
        data.specs = JSON.stringify(vm.specs);
        data.content = layedit.getContent(edit_content);

        if (action == "add") {
            action = "admin.shop.goods_add";
        } else if (action == "edit") {
            action = "admin.user.goods_edit";
        }
        data.action = action;
        $.ajax({
            url: "/",
            method: "POST",
            data,
            success: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.description);
                    return;
                }
                location.reload();
            }
        });
        return false;
    });

    $("#select_goods").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("order_list", {
            page: {
                curr: 1
            },
            where: {
                name: sel_val
            }
        })

        return false;
    })

    table.render({
        elem: '#order_list'
        , url: '/?action=admin.shop.order_list'
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "order_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'expressUserName', align: "center", title: '下单用户名' },
            { field: 'goods[0].gid.title', align: "center", title: '商品列表' },
            { field: 'receiver', align: "center", title: '收件人' },
            { field: 'address', align: "center", title: '收件地址' },
            { field: 'expressNo', align: "center", title: '快递单号' },
            { field: 'expressCompany', align: "center", title: '快递公司' },
            { field: 'price', align: "center", title: '订单金额' },
            { field: 'realPrice', align: "center", title: '实付金额' },
            { title: '支付状态', fixed: "right", align: "center", toolbar: "#payState_item" },
            { field: 'createDate', align: "center", title: '下单时间' },
            { title: '订单状态', fixed: "right", align: "center", toolbar: "#state_item" },

            { align: "center", width: 100, title: "已发货", toolbar: "#use_status" },
            { title: '操作', fixed: "right", width: 210, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res.descriptions || "请求出错!!!");
                return {};
            }
            var orderList = res.order_list
            orderList = orderList.map(order_info => {
                order_info.createDate = vm.formatDate(order_info.createDate, 'YYYY-MM-DD HH:mm:ss')
                order_info.expressUserName = order_info.expressUserName || '无'
                order_info.goods[0].gid.title = order_info.goods[0].gid.title || '无'
                order_info.receiver = order_info.receiver || '无'
                order_info.address = order_info.address || '无'
                order_info.expressNo = order_info.expressNo;
                order_info.expressCompany = order_info.expressCompany;
                if (order_info.state == '待付款') {
                    order_info.stateName = "layui-btn layui-btn-xs layui-btn layui-btn-primary";
                } else if (order_info.state == '待发货') {
                    order_info.stateName = "layui-btn layui-btn-xs layui-btn layui-btn-warm";
                } else if (order_info.state == '待收货') {
                    order_info.stateName = "layui-btn layui-btn-xs layui-btn layui-btn-normal";
                } else {
                    order_info.stateName = "layui-btn layui-btn-xs layui-btn";
                }
                if (order_info.payState == '待付款') {
                    order_info.payStateName = "layui-btn layui-btn-xs layui-btn layui-btn-warm";
                    
                } else if (order_info.payState == '已付款') {
                    order_info.payStateName = "layui-btn layui-btn-xs layui-btn";
                } else {
                    order_info.payStateName = "layui-btn layui-btn-xs layui-btn layui-btn-danger";

                }
                return order_info;
            })

            return {
                "code": res.errcode,
                "data": orderList,
                "count": res.total,
                "limit": 15
            }
        }
    });

    table.on("tool(orders)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "edit":
                $.ajax({
                    url: "/?action=admin.shop.goods_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        var goodsInfo = res.goodsInfo;
                        $("input[name=title]").val(goodsInfo.title);
                        $("input[name=price]").val(goodsInfo.price);
                        $("input[name=stock]").val(goodsInfo.stock);
                        vm.specs = goodsInfo.specs;
                        $("input[name=stock]").val(goodsInfo.stock);
                        vm.imgs = goodsInfo.pics;
                        vm.logoImg = goodsInfo.thumb;
                        vm.detailImg = goodsInfo.logo

                        action = "edit";
                        var idx = layer.open({
                            title: `${data.nickname}的详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "650px"],
                        });
                        layer.full(idx);
                    }
                })
                break;
            case "detail":
                $.ajax({
                    url: "/?action=admin.shop.order_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        $(".expressUserName").html(res.orderInfo.uid.username || res.orderInfo.uid.nickname || '无');
                        $(".goods_title").html(res.orderInfo.goods[0].gid.title || '无');
                        $(".receiver").html(res.orderInfo.expressUserName + '/' + res.orderInfo.expressUserMobile || '无');
                        $(".address").html(res.orderInfo.expressUserAddress.province + res.orderInfo.expressUserAddress.city + res.orderInfo.expressUserAddress.district + res.orderInfo.expressUserAddress.detail || '无');
                        $(".expressNo").html(res.orderInfo.expressNo || '无');
                        $(".price").html(res.orderInfo.price || '无');
                        $(".realPrice").html(res.orderInfo.realPrice || '无');
                        $(".expressCompany").html(res.orderInfo.expressCompany || '无');
                        $(".payState").html(res.orderInfo.payState || '无');
                        $(".state").html(res.orderInfo.state || '无');
                        $(".createDate").html(vm.formatDate(res.orderInfo.createDate, 'YYYY-MM-DD HH:mm:ss') || '无');

                        layer.open({
                            title: "商品详情",
                            type: 1,
                            content: $(".detail-subcat"),
                            area: ['600px', '450px']
                        });
                    }
                })
                break;
        }
    });
    $("#express_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};

        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        console.log("ceshi ", data)
    })
    form.on("switch(is_confirm)", function (obj) {
        layer.open({
            title: "填写快递信息",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "300px"]
        })
        vm.checked = obj.elem.checked;
        vm.id = obj.value;
    });

    form.on("submit(add_express)", function (obj) {
        $.ajax({
            url: "/?action=admin.shop.order_send_express",
            method: "GET",
            data: {
                checked: vm.checked,
                id: vm.id,
                company: obj.field.express_company,
                expressNo: obj.field.express_num,

            },
            success: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.description);
                }
                location.reload();
            }
        })
        return false;
    })


});