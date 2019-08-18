layui.use(["table", "jquery", "layer", "laydate", "form"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var form = layui.form;

    var vm = new Vue({
        el: "#container",
        data: {
            start_time: "",
            end_time: "",
            total: 0
        },
        methods: {

        }
    });

    laydate.render({
        elem: "#start_time",
        type: "time",
        min: "8:00:00",
        max: "17:00:00"
    });

    laydate.render({
        elem: "#end_time",
        type: "time",
        min: "8:00:00",
        max: "17:00:00"
    });

    table.render({
        elem: "#timer_list",
        url: "/activity/admin/timerList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "timer_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'start_time', align: "center", title: '开始时间' },
            { field: 'end_time', align: "center", title: '结束时间' },
            { field: 'total', align: "center", title: '可训练人数' },
            { field: 'rest_count', align: "center", title: '剩余名额' },
            { field: 'position', align: "center", title: '排序', edit: "text" },
            { toolbar: "#use_status", align: "center", title: '启用状态' },
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
                "data": res.data.timer_list,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    table.on("edit(timers)", function(obj) {
        var value = obj.value;
        var data = obj.data;

        $.ajax({
            url: "/activity/admin/timerQueue",
            data: {
                value,
                id: data._id
            },
            success: function(res) {
                if(!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "请求出错");
                    return;
                }
                layer.msg("操作成功");
            }
        })
    })

    form.on("switch(used)", function(obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        checked = +checked;
        $.ajax({
            url: "/activity/admin/timerState",
            data: {
                id,
                checked
            },
            success: function(res) {
                if(!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "请求有误!!!");
                    return;
                }
                layer.msg("操作成功");
            }
        })
    })

    $(".modal").click(function () {
        vm.total = 0;
        vm.start_time = "";
        vm.end_time = "";

        layer.open({
            title: "添加时间段",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "350px"]
        });
    })

    table.on("tool(timers)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/timerDel",
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
                case "":
        }
    });

    $("#goods_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        $.ajax({
            url: "/activity/admin/timerAdd",
            data,
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