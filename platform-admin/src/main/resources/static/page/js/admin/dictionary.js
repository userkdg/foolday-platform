var vm = new Vue({
    el: "#container",
    data: {
    	dictionaryName: ""

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
    	console.log(">>>>>dictionaryName", vm.dictionaryName);
        $.ajax({
        contentType: 'application/json;charset=UTF-8',
         dataType: "json",
            url: "/admin/Dictionary/save",
            method: "POST",
            data: JSON.stringify({
            	"dictionaryName": vm.dictionaryName
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
        elem: '#dictionary_list'
        , url: '/admin/Dictionary/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'dictionaryName', title: '栏目名' },
           //  { toolbar: '#status', title: '栏目状态' },
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
                     "count": res.data.numberOfElements,
                     "limit": 18
                 }
        }
    });

form.on("switch(status)", function (obj) {
        console.log(">>>>>obj", obj);
        var checked = obj.elem.checked;
        var dictionaryId = obj.value;
        var status = checked ? 1 : 0;
         console.log(status);
            $.ajax({
                url: "/admin/Dictionary/onFire",
               data:{
               dictionaryId,
               status
               },
                method: "GET",
                success: function (res) {
                    if (!res || res.errCode != 0) {
                        layer.msg("请求出错！！");
                        return;
                    }
                    layer.msg("操作成功");
                }
            })


    });
    table.on("tool(dictionarys)", function (obj) {
        var data = obj.data;
        console.log(">>>>>>>>>>>data"+data);
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/Dictionary/delete/"+data.dictionaryId,
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