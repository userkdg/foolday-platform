var vm = new Vue({
    el: "#container",
    data: {
    	adviceId: "",
    	adviceUrl: "",
    	openid: "",
    	adviceText:""
        
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
    	console.log(">>>>>", vm.adviceId, vm.adviceUrl,vm.openid,vm.adviceText);
    	var allData={  "adviceUrl":vm.adviceUrl,
                       "adviceId": vm.adviceId,
                       "openid": vm.openid,
                       "adviceText": vm.adviceText};
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            url: "/admin/Advice/save",
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
        elem: '#feedback_list'
        , url: '/admin/Advice/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'openid', title: '用户id' },
            { field: 'adviceText', title: '反馈内容' },
            { field: 'adviceUrl', title: '意见反馈url' },
            { title: '操作', align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            console.log(">>>>>", res);
            if (res.code != 0) {
                layer.msg(res.errMsg);
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
    //崇信加载数据表格
        var $ = layui.$, active = {
                reload: function(){

                    table.reload('manager_list', {

                    });
                }
            };

    table.on("tool(feedbacks)", function (obj) {
        var data = obj.data;
        console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",data);
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/Advice/delete/"+data.adviceId,
//                            data: {
//                                adviceId: data.adviceId
//                            },
                            success: function (res) {
                            console.log(res);
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
                    url: "/admin/Advice/save",
                    method: "GET",
                    data: {
                        id: data.adviceId
                    },
                    success: function (res) {
                        if (res.errCode != 0) {
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