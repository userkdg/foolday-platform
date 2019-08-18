layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    var formatDate = function (datetime, pattern) {
        var date = new Date();
        date.setTime(datetime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        var hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
        var minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
        var second = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
        return pattern.replace(/YYYY/igm, year)
            .replace(/MM/gm, month)
            .replace(/DD/igm, day)
            .replace(/HH/igm, hour)
            .replace(/mm/gm, minute)
            .replace(/ss/gm, second);
    }

    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("user_list", {
            page: {
                curr: 1
            },
            where: {
                name: sel_val
            }
        });
        return false;
    })

    table.render({
        elem: '#user_list'
        , url: '/activity/admin/libBookUsers'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "user_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'nickname', title: '昵称' },
            { align: "center", width: 100, title: '头像', toolbar: "#user_logo" },
            { field: 'username', title: '真实姓名' },
            { field: 'sex', title: '性别' },
            { field: 'mobile', title: '联系方式' },
            { field: 'studentId', title: '学号' },
            { field: 'inTime', title: '入学时间' },
            { field: 'classroom', title: '班级' },
            { title: '操作', toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            res.data.users =  res.data.users.map(user => {
                user.inTime = formatDate(user.inTime, "YYYY-MM-DD");
                if(user.sex == 1) {
                    user.sex = "男"
                }else if(user.sex == 2) {
                    user.sex = "女"
                }
                return user;
            })
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
                            url: "/activity/admin/libUserDel",
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
});