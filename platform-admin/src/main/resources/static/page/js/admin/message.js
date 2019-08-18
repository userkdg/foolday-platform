var action = "add";

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;

    $("#msg_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};

        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        $.ajax({
            url: "/activity/admin/msgCourAdd",
            method: "GET",
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

    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("msg_list", {
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
        elem: '#msg_list'
        , url: '/activity/admin/msgCourList'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "msg_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'title', width: 180, title: '公告标题' },
            { field: 'content', title: '公告内容' },
            { title: "启用状态", fixed: "right", width: 180, align: "center", toolbar: "#use_status" },
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
                "data": res.data.msgs,
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
                            url: "/activity/admin/msgCourDel",
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
                $.ajax({
                    url: "/activity/admin/msgCourInfo",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        var message_info = res.data.info;
                        $("input[name=id]").val(message_info._id);
                        $("input[name=title]").val(message_info.title);
                        $("textarea[name=content]").val(message_info.content);
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

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        checked = checked ? 1 : 0;
        $.ajax({
            url: "/activity/admin/msgCourState",
            method: "GET",
            data: {
                checked,
                id
            },
            success: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.description);
                return
                }
                layer.msg("操作成功");
            }
        })
    });

    $(".modal").click(function () {
        $("#msg_info").get(0).reset();
        layer.open({
            title: "添加消息",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "400px"],
        })
    })
});