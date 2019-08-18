var vm = new Vue({
    el: "#UserSetting",
    data: {
        mobile: "",
        password: "",
        sel_mobile: ""
    },
    method: {

    }
})

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    $("#user_info").submit(function (e) {
        $.ajax({
            url: "/activity/admin/medical/medUserAdd",
            data: {
                mobile: vm.mobile,
                password: vm.password
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

    $("#select_user").submit(function (e) {
        table.reload("user_list", {
            page: {
                curr: 1
            },
            where: {
                mobile: vm.sel_mobile
            }
        })

        return false;
    })

    table.render({
        elem: '#user_list'
        , url: '/activity/admin/medical/medicalUsers'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "user_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'username', title: '账号' },
            { field: 'mobile', title: '联系方式' },
            { toolbar: '#used', title: '启用状态' },
            { title: '操作', toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.errcode,
                "data": res.data.users,
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
                            url: "/activity/admin/medical/medUserDel",
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
        }
    });

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;

        checked = checked ? 1 : 0;
        $.ajax({
            url: "/activity/admin/medical/medUserState",
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
        layer.open({
            title: "添加用户",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        });
    })
});