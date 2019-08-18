var vm = new Vue({
        el: "#container",
        data: {
        	routeId:"",
        	dictionaryId:"",
        	sel_name:'',
        	routes:"",
        	dictionarys:"",
        	sel_name_Dic:""

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

    form.on("select(sel_name_Dic)", function(obj) {
        vm.sel_name_Dic = obj.value;
    })

 $.ajax({
        url: "/admin/Route/findAll",
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

            vm.routes = res.data.content;
            console.log("routes>>>>>>",vm.routes)
        }
    })


 $.ajax({
        url: "/admin/Dictionary/findAll",
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

            vm.dictionarys = res.data.content;
            console.log("dictionarys>>>>>>",vm.dictionarys)
        }
    })


    $("#message_info").submit(function (e) {
    	//console.log(">>>>>", vm.adviceId, vm.adviceUrl,vm.openid,vm.adviceText);
       var allData={ "routeId":vm.routeId,
                      "dictionaryId":vm.dictionaryId};
               $.ajax({
                   contentType: 'application/json;charset=UTF-8',
                   dataType:"json",
                   url: "/admin/RouteDictionary/save",
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
        console.log(">>>>>>", vm.sel_name_Dic)
        table.reload("manager_list", {
            page: {
                curr: 1
            },
            where: {
                name: vm.sel_name,
                dicName:vm.sel_name_Dic
            }
        })

        return false;
    })

    table.render({
        elem: '#route_list'
        , url: '/admin/RouteDictionary/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
                  { field: 'route_title', title: '路线名称' },
                  { field: 'dictionary_name', title: '栏目名' },
                  { field: 'create_time', title: '创建时间' },
            { title: '操作', width:200, align: "center", toolbar: "#operator_item" }
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

    table.on("tool(feedbacks)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/RouteDictionary/delete/"+data.route_dictionary_id,
                            success: function (res) {
                                if (res.errCode != 0) {
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
            case "edit":
                $.ajax({
                    url: "/?action=admin.user.manager_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        var managerInfo = res.managerInfo;
                        $("input[name=realname]").val(managerInfo.nickname);
                        $("input[name=mobile]").val(managerInfo.mobile);
                        $("input[name=id]").val(managerInfo._id);
                        action = "edit";
                        form.render("select");
                        layer.open({
                            title: `${data.nickname}的详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "500px"],
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
    })
});