layui.use(['table', 'layer', "jquery", "form"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    var vm = new Vue({
        el: "#container",
        data: {
            id:"",
        	password:"",
        	username:"",
        	isAdmin:"",
        	merchantId:"",
        	merchants: [],
        	sel_name:"",
        	mer_name: ""

        },
        methods: {
            formarDate: function() {

            },
            seachByLoc: function() {
                $.ajax({
                    url: "/admin/Merchant/findAll",
                    method: "GET",
                    data: {
                        name: vm.mer_name
                    },
                    success: function(res) {
                    console.log("res>>>>>>>>>>",res)
                        if(!res || res.errCode != 0) {
                            layer.msg(res ? res.errMsg : "请求出错!");
                            return;
                        }
                        vm.merchants = res.data.content;

                        console.log(">>>>data", this.merchants);
                        setTimeout(function(){
                        console.log(">>>>>", "11111111111")
                            form.render("select");
                        }, 3000);
                    }
                })
            }
        }
    });

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

               $.ajax({
                   contentType: 'application/json;charset=UTF-8',
                   dataType:"json",
                   url: "/admin/User/save",
                   method: "POST",
                   data: JSON.stringify({
                        "id":vm.id,
                     	"password":vm.password,
                         	"username":vm.username,
                         	"isAdmin":vm.isAdmin,
                         	"merchantId":vm.merchantId
                   }),
                    success: function (res) {
                        vm.id = res.id;
                        if (res.errCode != 0) {
                            layer.msg(res.errMsg);
                            return;
                        }
                        location.reload();
                    }
        });
        return false;
    });

 $("#message_info2").submit(function (e) {

               $.ajax({
                   contentType: 'application/json;charset=UTF-8',
                   dataType:"json",
                   url: "/admin/User/save",
                   method: "POST",
                   data: JSON.stringify({
                        "id":vm.id,
                     	"password":vm.password,
                         	"username":vm.username,
                         	"isAdmin":vm.isAdmin,
                         	"merchantId":vm.merchantId
                   }),
                    success: function (res) {
                        vm.id = res.id;
                        if (res.errCode != 0) {
                            layer.msg(res.errMsg);
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
        elem: '#admin_list'
        , url: '/admin/User/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'merchant_name', title: '商家名' },
            { field: 'username', title: '账号' },
             { field: 'creat_time', title: '创建时间' },
             {toolbar:"#operator_item",title:"操作"}
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
    table.on("tool(admins)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/User/delete/"+data.id,
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
            case "edit":
                $.ajax({
                    url: "/admin/User/findOne/"+data.id,
                    method: "GET",
                    success: function (res) {
                   if (!res || res.errCode != 0) {
                    layer.msg(res ? res.errMsg : "请求出错");
                    return;
                     }
                     var info=res.data;
                      vm.id = info.id;
                      console.log(vm.id)
//                      vm.password = info.password;
                      vm.username = info.username;
                      vm.isAdmin = info.isAdmin;
                      vm.merchantId = info.merchantId;
                        form.render("select");
                        layer.open({
                            title: `修改密码`,
                            type: 1,
                            content: $(".edit2"),
                            area: ["650px", "200px"],
                        });
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

        form.render("select");
    })
});