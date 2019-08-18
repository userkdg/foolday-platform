var vm = new Vue({
    el: "#container",
    data: {
        title: "",
        content: ""
    },
    methods: {
    }
})

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var layedit = layui.layedit;

    $("#msg_info").submit(function (e) {
        $.ajax({
            url: "/activity/admin/msgAdd",
            method: "GET",
            data: {
                title: vm.title,
                content: vm.content
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

    $("#select_goods").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("msg_list", {
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
        elem: '#msg_list'
        , url: '/activity/admin/msgList'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "msg_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'title', align: "center", title: "消息标题" },
            { field: 'content', align: "center", title: '消息内容' },
            { align: "center", width: 100, title: "上架状态", toolbar: "#use_status" },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!!");
                return {};
            }
            return {
                "code": res.errcode,
                "data": res.data.books,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;

        checked = checked ? 1 : 0;
        $.ajax({
            url: "/activity/admin/msgState",
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
                layer.msg("操作成功!");
            }
        })
    })

    table.on("tool(messages)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/msgDel",
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

    $(".modal").click(function () {
        layer.open({
            title: "添加消息",
            type: 1,
            content: $(".add-subcat"),
            area: ["700px", "350px"]
        });
    });
});