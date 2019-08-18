layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    var vm = new Vue({
        el: "#Message",
        data: {
            title: "",
            content: "",
            id: ""
        }
    })

    $("#message_info").submit(function (e) {
        $.ajax({
            url: "/activity/admin/msgAdd",
            method: "GET",
            data: {
                title: vm.title,
                content: vm.content,
                id: vm.id
            },
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

    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("message_list", {
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
        elem: '#message_list'
        , url: '/activity/admin/msgList'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "message_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'title', width: 180, title: '公告标题' },
            { field: 'content', title: '公告内容' },
            { title: "启用状态", fixed: "right", width: 180, align: "center", toolbar: "#status" },
            { title: '操作', fixed: "right", width: 180, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.errcode,
                "data": res.data.messages,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    table.on("tool(messages)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/msgDel",
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
            case "edit":
                action = "edit";
                $.ajax({
                    url: "/activity/admin/msgDetail",
                    method: "GET",
                    data: {
                        id: data._id,
                        title:data.title,
                        content:data.content
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        
                        var info = res.data.info;
                        vm.title = info.title;
                        vm.content = info.content;
                        vm.id = info._id.toString();
                        
                        layer.open({
                            title: `${data.title}的详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "400px"],
                        });
                    }
                })
                break;
        }
    });

    form.on("switch(status)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;

        checked = checked ? 1 : 0;
        $.ajax({
            url: "/activity/admin/msgState",
            method: "GET",
            data: {
                checked,
                id
            },
            success: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.description);
                    return;
                }
                layer.msg("操作成功");
            }
        })
    });

    $(".modal").click(function () {
        $("#message_info").get(0).reset();
        action = "add";
        layer.open({
            title: "添加系统消息",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "400px"],
        })
    })
});