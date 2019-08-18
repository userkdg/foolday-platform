var vm = new Vue({
        el: "#container",
        data: {
            upload_result: "未上传",
            img: ""
        },
        methods: {
            add: function() {
                $.ajax({
                    url: "/file/upload",
                    method: "POST",
                    data: info,
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        location.reload();
                    }
                });
            },
            del: function(index) {

            },
            clear: function(index) {
                vm.img = "";
                vm.upload_result = "未上传";
            }
        }
    })
layui.use(['table', 'layer', "upload", "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var upload = layui.upload;
    var $ = layui.jquery;
    
    $("#advercise_info").submit(function (e) {
        vm.add();
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
    });

    upload.render({
        elem: "#choose_img",
        url: "/file/upload",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.img = result;
            });
        },
        done: function (res) {
        	console.log(">>>>>>res", res.data.file);
            if (res && res.errCode == 0) {
                vm.upload_result = "上传成功!";
                vm.img = res.data.file;
            } else {
                vm.upload_result = "上传失败!!!";
            }
        }
    });

    table.render({
        elem: '#adver_list'
        , url: '/?action=admin.system.adver_list'
        , cellMinWidth: 80 
        , limit: 15,
        limits: [15],
        id: "adver_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { title: "图片内容", width: 180, toolbar: "#img_detail" },
            { title: "启用状态", fixed: "right", width: 180, align: "center", toolbar: "#status" },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.errcode,
                "data": res.adver_list,
                "count": res.total,
                "limit": 15
            }
        }
    });

    table.on("tool(advercises)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/adverDel",
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

        checked = checked ? 1 : 0;
        $.ajax({
            url: "/?action=admin.system.adver_status",
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
        layer.open({
            title: "添加广告",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "300px"],
        })
    })
});