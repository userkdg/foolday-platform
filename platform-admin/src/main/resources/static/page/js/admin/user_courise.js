var action = "add";

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;

    $.ajax({
        url: "/?action=admin.user.role_list",
        method: "get",
        success: function (res) {
            if (res.errcode == 0) {
                var options = ""
                res.roles.map(role => {
                    options += '<option value="' + role._id + '">' + role.name + '</option>'
                    return role;
                })
                $("#roles").append(options);
            }
        }
    });

    $("#user_info").submit(function (e) {
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
        table.reload("user_list", {
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
        elem: '#user_list'
        , url: '/activity/admin/courUsers'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "user_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'nickname', width: 180, title: '昵称' },
            { align: "center", width: 100, title: '头像', toolbar: "#user_logo" },
            { field: 'username', width: 100, title: '真实姓名' },
            { field: 'sex', width: 80, title: '性别' },
            { field: 'mobile', width: 180, title: '联系方式' },
            { field: 'studentId', title: '学号' },
            { field: 'inTime', title: '入学年份' },
            { field: 'classroom', title: '班级' },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            var users = res.data.users.map(user => {
                user.sex = user.sex == 2 ? "女" : user.sex == 1 ? "男" : "未知";
                return user;
            })
            return {
                "code": res.errcode,
                "data": users,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    table.on("tool(users)", function (obj) {
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
            case "detail":
                $.ajax({
                    url: "/?action=admin.user.user_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        let info = res.user_info;
                    
                        $(".nickname").html(info.nickname);
                        $(".username").html(info.username || '无');
                        $(".mobile").html(info.mobile || '无');
                        $(".level").html(info.level);
                        $(".commission_grade").html(info.commission_grade.name);
                        var parent = info.parent_id;
                        var p_parent = info.p_parent_id;
                        var parentName = '无';
                        var p_parentName = '无'
                        if(parent) parentName = parent.username || parent.nickname;
                        if(p_parent) p_parentName = p_parent.username || p_parent.nickname;
                        
                        $(".parent_Name").html(parentName);
                        $(".p_parent_Name").html(p_parentName);
                        $(".partner").html(info.is_partner?'是':'否');
                        $(".commission").html(info.is_commission?'是':'否');
                        $(".joined").html(info.is_joined?'是':'否');

                        layer.open({
                            title: "用户详情",
                            type: 1,
                            content: $(".detail-subcat"),
                            area: ['600px', '460px']
                        });
                    }
                })
                break;
        }
    });

    form.on("switch(partner)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.user.user_partner",
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

    form.on("switch(commission)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.user.user_commission",
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

    form.on("switch(joined)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.user.user_joined",
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