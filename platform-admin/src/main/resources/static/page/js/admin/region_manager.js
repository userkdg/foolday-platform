var action = "add";

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;

    $.ajax({
        url: "/?action=admin.user.role_list",
        method: "get",
        success: function(res) {
            if(res.errcode == 0) {
                var options = ""
                res.roles.map(role => {
                    options += '<option value="' + role._id + '">' + role.name + '</option>'
                    return role;
                })
                $("#roles").append(options);
            }
        }
    });

    $("#manager_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }

        if (action == "add") {
            action = "admin.user.manager_add";
        } else if (action == "edit") {
            action = "admin.user.manager_update";
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
        elem: '#manager_list'
        , url: '/?action=admin.user.manager_list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'nickname', width: 180, title: '姓名' },
            { field: 'roleid', title: '所属角色' },
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
                "data": res.admins,
                "count": res.total,
                "limit": 15
            }
        }
    });

    table.on("tool(managers)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/?action=admin.user.manager_delete",
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
                        $("input[name=nickname]").val(managerInfo.nickname);
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
        $("#manager_info").get(0).reset();

        action = "add";
        layer.open({
            title: "添加角色",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        })
    })
});