layui.use(["table", "jquery", "layer"], function () {
    var $ = layui.jquery;
    var table = layui.table;

    var vm = new Vue({
        el: "#container",
        data: {
            name: "",
            mobile: ""
        },
        methods: {

        }
    });

    table.render({
        elem: "#coach_list",
        url: "/activity/admin/coachList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "coach_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'name', align: "center", title: '姓名' },
            { field: 'mobile', align: "center", title: '联系方式' },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" },
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            return {
                "code": res.errcode,
                "data": res.data.coach_list,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    $(".modal").click(function () {
        vm.name = "";
        vm.mobile = "";

        layer.open({
            title: "添加教练",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "350px"]
        });
    })

    table.on("tool(coachs)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/coachDel",
                            data: {
                                id: data._id
                            },
                            success: function(res) {
                                if(!res || res.errcode != 0) {
                                    layer.msg(res ? res.description : "请求失败!!!");
                                    return;
                                }
                                layer.msg("操作成功!!!");
                                location.reload();
                            }
                        });
                        return true;
                    }
                });
                break;
        }
    });

    $("#goods_info").submit(function (e) {
        $.ajax({
            url: "/activity/admin/coachAdd",
            data: {
                name: vm.name,
                mobile: vm.mobile
            },
            success: function (res) {
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "请求出错!!!");
                    return false;
                }
                location.reload();
            }
        });
        return false;
    });
});