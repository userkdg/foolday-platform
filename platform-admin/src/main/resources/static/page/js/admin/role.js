layui.config({
    base: "/activity/layui/"
}).extend({
    formSelects: "formSelects-v4.min"
});

var action = "add";

layui.use(['table', 'layer', "jquery", 'formSelects'], function () {
    var table = layui.table;
    var formSelects = layui.formSelects;
    var roleConent;

    formSelects.config("role_content", {
        beforeSuccess: function (id, url, searchVal, result) {
            roleConent = result.data.data;
            return roleConent;
        }
    }).data("role_content", "server", {
        url: "/activity/admin/funList"
    });

    //待解决的问题，当子选项选择之后父选项会被默认选中，那么当父选项的子选项全部被清除时，复选项也应该置为未选中
    formSelects.on("role_content", function (id, vals, val, isAdd, isDiable) {
        var child = parseInt(val.value);
        if (child < 10) {
            var childrens = [];
            for (var item of roleConent) {
                if (item.value != child) continue;
                if (item.children.length != 0) {
                    childrens = item.children.map(item => {
                        return item.value;
                    });
                }
            }
            formSelects.value("role_content", childrens, isAdd)
        }
        var parent = Math.floor(child / 10);
        if (parent > 0) {
            formSelects.value("role_content", [parent], true);
        }
    });

    $("#role_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }

        var vals = formSelects.value("role_content");
        var roles = {}
        for (var val of vals) {
            roles[val.value] = val.name;
        }
        roles = JSON.stringify(roles);
        var obj = {
            name: data.name,
            roles: roles,
            id: data.id
        };
        $.ajax({
            url: "/activity/admin/roleAdd",
            method: "POST",
            data: obj,
            success: function (res) {
                if (res.errcode != 0) {
                    layer.open({
                        title: "系统提示",
                        content: res.description
                    });
                    return;
                }
                location.reload();
            }
        });
        return false;
    });

    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("role_list", {
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
        elem: '#role_list'
        , url: '/activity/admin/roleList'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "role_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'name', width: 180, title: '角色名称' },
            { field: 'permissions', title: '权限内容' },
            { title: '操作', fixed: "right", width: 180, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求有误!");
                return;
            }

            return {
                "code": res.errcode,
                "data": res.data.roles,
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
                            url: "/activity/admin/roleDel",
                            method: "GET",
                            data: {
                                id: data._id
                            },
                            success: function (res) {
                                if (!res || res.errcode != 0) {
                                    layer.msg(res ? res.description : "请求出错!!");
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
                    url: "/?action=admin.system.role_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        $("input[name=name]").val(data.name);
                        $("input[name=id]").val(data._id)
                        formSelects.value("role_content", res.roleInfo.permissions, true);
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
    })

    $(".modal").click(function () {
        $("input[name=name]").val("");

        formSelects.value("role_content", []);
        action = "add";
        layer.open({
            title: "添加角色",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        })
    })
});