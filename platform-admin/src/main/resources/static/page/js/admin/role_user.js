var action = "add";

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;

    $("#user_role_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }

        if (action == "add") {
            action = "admin.system.user_role_add";
        } else if (action == "edit") {
            action = "admin.system.user_role_update";
        }
        data.action = action;
        $.ajax({
            url: "/",
            method: "POST",
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
        table.reload("user_role_list", {
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
        elem: '#user_role_list'
        , url: '/?action=admin.system.user_role_list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "user_role_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'name', width: 180, title: '角色名称' },
            { field: 'parent_money', title: '一级佣金' },
            { field: 'p_parent_money', title: '二级佣金' },
            { field: 'conditions', title: '升级条件' },
            { field: 'bonus_rate', title: '分红比例' },
            { field: 'order', title: '优先级' },
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
                "data": res.levels,
                "count": res.total,
                "limit": 15
            }
        }
    });

    table.on("tool(roles)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/?action=admin.system.user_role_delete",
                            method: "POST",
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
                    url: "/?action=admin.system.user_role_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        var user_role_info = res.level;
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        $("input[name=name]").val(user_role_info.name);
                        $("input[name=parent_money]").val(user_role_info.parent_money);
                        $("input[name=p_parent_money]").val(user_role_info.p_parent_money);
                        $("input[name=conditions]").val(user_role_info.conditions);
                        $("input[name=bonus_rate]").val(user_role_info.bonus_rate);
                        $("input[name=order]").val(user_role_info.order);
                        $("input[name=id]").val(user_role_info._id);
                        layer.open({
                            title: `${data.name}的详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "500px"],
                        });
                    }
                })
                break;
        }
    });

    form.on("switch(status)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.system.user_role_status",
            method: "GET",
            data: {
                checked,
                id
            },
            success: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.description);
                }
            }
        })
    });

    $(".modal").click(function () {
        $("#user_role_info").get(0).reset();

        action = "add";
        layer.open({
            title: "添加角色",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        })
    })
});