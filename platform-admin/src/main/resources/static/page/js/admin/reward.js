var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
    },
    methods: {
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

    $("#select_goods").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("reward_list", {
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
        elem: '#reward_list'
        , url: '/?action=admin.reward.reward_record'
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "reward_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号'},
            { field: 'uid', align: "center", title: '用户名'},
            { field: 'oid', align: "center", title: '订单号'},
            { field: 'before_money', align: "center", title: '到账前余额'},
            { field: 'money', align: "center", title: '分享奖励' },
            { field: 'after_money', align: "center", title: '到账后余额'},
            { field: 'remark', align: "center", title: '备注'},
            { field: 'createDate', align: "center", title: '到账时间'}
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res.descriptions || "请求出错!!!");
                return {};
            }
            var orderList = res.record_list
            orderList = orderList.map(order_info => {
                order_info.uid = order_info.uid.nickname || order_info.uid.username || "无";
                order_info.createDate = vm.formatDate(order_info.createDate, 'YYYY-MM-DD HH:mm:ss')
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

    table.on("tool(rewards)", function (obj) {
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
                        $(".expressUserName").html(res.orderInfo.expressUserName || '无');
                        $(".goods_title").html(res.orderInfo.goods[0].gid.title || '无');
                        $(".receiver").html(res.orderInfo.receiver || '无');
                        $(".address").html(res.orderInfo.address || '无');
                        $(".expressNo").html(res.orderInfo.expressNo || '无');
                        $(".price").html(res.orderInfo.price || '无');
                        $(".realPrice").html(res.orderInfo.realPrice || '无');
                        $(".expressCompany").html(res.orderInfo.expressCompany || '无');
                        $(".payState").html(res.orderInfo.payState || '无');
                        $(".state").html(res.orderInfo.state || '无');
                        $(".createDate").html(res.orderInfo.createDate || '无');


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
});