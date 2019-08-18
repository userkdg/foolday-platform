var vm = new Vue({
    el: "#container",
    data: {
    	discountId: "",
    	price: "",
    	discountType: "",
    	discountNum:0,
    	condition: "",
        beginTime: "",
        endTime: "",
        discountStatus:"",
        merchantId:"",
        merchants: [],
        sel_name:""
        
    },
    methods: {
        formarDate: function() {
        	
        }
    }
});

layui.use(['table', 'layer', "jquery", "form"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

$.ajax({
        url: "/admin/Merchant/findAll",
        method: "GET",
        data: {
            page: 1,
            size: 10000
        },
        success: function(res) {
            if(!res || res.errCode != 0) {
                layer.msg(res ? res.errMsg : "请求出错!");
                return;
            }

            vm.merchants = res.data.content;
        }
    })
    $("#message_info").submit(function (e) {
    	//console.log(">>>>>", vm.adviceId, vm.adviceUrl,vm.openid,vm.adviceText);

               $.ajax({
                   contentType: 'application/json;charset=UTF-8',
                   dataType:"json",
                   url: "/admin/Discount/save",
                   method: "POST",
                   data: JSON.stringify({
                       "discountId": "",
                      "price": vm.price,
                      "discountType": vm.discountType,
                      "discount_num":0,
                      "condition": vm.condition,
                      "beginTime": vm.beginTime,
                      "endTime": vm.endTime,
                      "discountStatus":vm.discountStatus,
                       "merchantId": vm.merchantId
                   }),
                    success: function (res) {
                        vm.id = res.id;
                        if (res.errCode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        location.reload();
                    }
        });
        return false;
    });

    $("#select_role").submit(function (e) {

        table.reload("manager_list", {
            page: {
                curr: 1
            },
            where: {
                name: vm.sel_name
            }
        })

        return false;
    })

    table.render({
        elem: '#route_list'
        , url: '/admin/Discount/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'merchant_name', title: '所属商家' },
            { field: 'price', title: '满足金额' },
            { toolbar: '#discount_status', title: '优惠券类型' },
              { field: 'condition', title: '优惠金额' },
             { toolbar:"#status", title: '上架状态' },
             { field: 'begin_time', title: '开始时间' },
             { field: 'end_time', title: '结束时间' }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errCode != 0) {
                layer.msg(res.description);
                return {};
            }
             return {
                           "code": res.code,
                           "data": res.data.content,
                           "count": res.data.totalElements,

                           "limit": 18
                       }
        },
        done:{

        }
    });
 form.on("switch(status)", function (obj) {
        console.log(">>>>>obj", obj);
        var checked = obj.elem.checked;
        var discountId = obj.value;
        console.log(checked);
        var status = checked ? 1 : 0;
        $.ajax({
            url: "/admin/Discount/onSale",
            method: "GET",
            data: {
                discountId,
                status
            },
            success: function (res) {
                if (res.errCode != 0) {
                    layer.msg("请求出错！！");
                    return;
                }
                layer.msg("操作成功");
            }
        })
    });
    table.on("tool(feedbacks)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要核销吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/Discount/change/"+data.discount_id,
                            success: function (res) {
                                if (res.errCode != 0) {
                                    layer.msg(res.errMsg);
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

    $(".modal").click(function () {
        layer.open({
            title: "添加",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        });

        form.render("select");
    })
});