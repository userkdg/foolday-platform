var vm = new Vue({
    el: "#container",
    data: {
        sel_user: "",
        type: 2
    },
    methods: {

    }
})
layui.use(['table', 'layer', "jquery", "upload", "form", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.jquery;

    $("#select_hotal").submit(function (e) {
        table.reload("order_list", {
            page: {
                curr: 1
            },
            where: {
                sel_user: vm.sel_user,
                type: vm.type
            }
        })
        return false;
    })

    table.render({
        elem: '#order_list'
        , url: '/activity/admin/hotal/orderList'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "order_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'uid', align: "center", title: '所属用户' },
            { toolbar:"#type_status" , align: "center", title: '订单类型' },
            { field: 'name', align: "center", title: '订单明细' },
            { field: 'count', align: "center", title: '数量' },
            { field: 'money', align: "center", title: '订单金额' },
            { field: 'real_money', align: "center", title: '实付金额' },
            { title: "支付状态", toolbar: "#pay_status" },
            { title: "订单状态", toolbar: "#order_status" },
            { title: '操作', align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!!");
                return {};
            }
            var records = res.data.records;
            records = records.map(record => {
                var spec = record.goods[0].gspec;
                record.name = (record.tourInfo ? record.tourInfo.name : "") + "(" + spec.name + "/" + spec.price + ")";
                return record;
            })
            
            return {
                "code": res.errcode,
                "data": records,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    table.on("tool(orders)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/hotal/orderDel",
                            method: "GET",
                            data: {
                                id: data._id
                            },
                            success: function (res) {
                                if (res.errcode != 0) {
                                    layer.msg(res.description);
                                    return;
                                }
                                location.reload();
                            }
                        })
                        return true;
                    }
                })
                break;
        }
    });
});