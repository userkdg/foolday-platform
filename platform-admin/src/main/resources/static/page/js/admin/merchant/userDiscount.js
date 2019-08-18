var vm = new Vue({
    el: "#container",
    data: {
    	discountId: "",
    	price: "",
    	discountType: "",
    	discountNum:"",
    	condition: "",
        beginTime: "",
        endTime: "",
        discountStatus:"",
        merchantId: ""

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

    $("#message_info").submit(function (e) {
    	//console.log(">>>>>", vm.adviceId, vm.adviceUrl,vm.openid,vm.adviceText);
       var allData={ "discountId": "",
                         	"price": vm.price,
                         	"discountType": vm.discountType,
                         	"discountNum":vm.discountNum,
                         	"condition": vm.condition,
                             "beginTime": vm.beginTime,
                             "endTime": vm.endTime,
                             "discountStatus":vm.discountStatus,
                             "merchantId": vm.merchantId };
               $.ajax({
                   contentType: 'application/json;charset=UTF-8',
                   dataType:"json",
                   url: "/admin/Discount/save",
                   method: "POST",
                   data: JSON.stringify(allData),
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
        var sel_val = $("input[name=sel_name]").val();

        var id = vm.id;
        table.reload("manager_list", {
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
        elem: '#route_list'
        , url: '/admin/UserDiscount/findAllWithMerchant'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'user_name', title: '用户名' },
            { field: 'begin_time', title: '领取时间' },
            { field: 'end_time', title: '结束时间' },
            { toolbar: '#userStatus', title: '使用状态' },
            {toolbar:'#operator_item',title:'操作'}
        ]],
        page: true,
        parseData: function (res) {
            console.log(">>>>>", res);
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
                                url: "/admin/UserDiscount/change/"+data.user_id,
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
            title: "添加公告",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        });
    })
});