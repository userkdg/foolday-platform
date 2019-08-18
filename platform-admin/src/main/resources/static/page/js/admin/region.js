var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
        regions: [{name: "请选择所属区域", grade: -1}],
        mobile: "",
        realname: "",
        region: 0,
        id: ""
    },
    methods: {
        
    }
});

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    $.ajax({
        url: "/?action=admin.user.addr_list",
        method: "get",
        success: function(res) {
            if(res.errcode == 0) {
                vm.regions = res.addr_list;
            }
        }
    });

    $("#region_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        console.log(">>>data", data);
        var index = parseInt(data.region);
        data.region = JSON.stringify(vm.regions[index]);
        alert(data);
        if (action == "add") {
            action = "admin.user.region_add";
        } else if (action == "edit") {
            action = "admin.user.region_edit";
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
        table.reload("region_list", {
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
        elem: '#region_list'
        , url: '/?action=admin.user.region_list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "region_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'username', width: 180, title: '登录账号' },
            { field: 'nickname', width: 180, title: '姓名' },
            { field: 'region', title: '管辖区域' },
            { field: 'count', width: 180, title: '区域总单量' },
            { title: '操作', width: 180, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return;
            }
            res.region_list = res.region_list.map(item => {
                item.region = `${item.region.province}/${item.region.city}`;
                return item;
            })
            return {
                "code": res.errcode,
                "data": res.region_list,
                "count": res.total,
                "limit": 15
            }
        }
    });

    table.on("tool(regions)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/?action=admin.user.region_del",
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
                    url: "/?action=admin.user.region_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        var managerInfo = res.info;
                        console.log(">>>>>", managerInfo);
                        vm.realname = managerInfo.nickname;
                        vm.mobile = managerInfo.mobile;
                        vm.id = managerInfo._id;
                        layer.open({
                            title: `编辑${managerInfo.nickname}的信息`,
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
        $("#region_info").get(0).reset();

        action = "add";
        layer.open({
            title: "添加区域代理",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        })
    })
});