var vm = new Vue({
    el: "#container",
    data: {
        name: "",
        author: "",
        press: "",
        total: "",
        bookNo: "",
        content: "",
        thumb: "",
        upload_result: "上传中...",
        rentUser: "",
        bookName: ""
    },
    methods: {
        clear_path: function () {
            vm.thumb = "";
            vm.upload_result = "上传中...";
        },
        formatDate: function (datetime, pattern) {
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
    }
})

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;

    $("#select_goods").submit(function (e) {
        table.reload("record_list", {
            page: {
                curr: 1
            },
            where: {
                rentUser: vm.rentUser,
                bookName: vm.bookName
            }
        })
        return false;
    })

    table.render({
        elem: '#record_list'
        , url: '/activity/admin/rentList'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "record_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'name', align: "center", title: '租借人' },
            { field: 'author', align: "center", title: '书籍名称' },
            { title: "书籍照片", align: "center", toolbar: "#goods_logo" },
            { field: 'rentDay', align: "center", title: '租借天数' },
            { field: 'rentDate', align: "center", title: '归还日期' },
            { toolbar: "#status", title: '归还状态' },
            { field: 'createDate', align: "center", title: '借出时间' },
            { title: '操作', width: 100, toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!!");
                return {};
            }
            res.data.rents = res.data.rents.map(rent => {
                rent.author = rent.bookId.name;
                rent.thumb = rent.bookId.thumb;
                rent.name = rent.uid ? rent.uid.username : "已删除";
                rent.rentDate = vm.formatDate(rent.rentDate, "YYYY-MM-DD HH:mm:ss");
                rent.createDate = vm.formatDate(rent.createDate, "YYYY-MM-DD HH:mm:ss");
                return rent;
            })
            return {
                "code": res.errcode,
                "data": res.data.rents,
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
            url: "/activity/admin/libBookState",
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

    table.on("tool(records)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/rentDel",
                            method: "GET",
                            data: {
                                id: data._id
                            },
                            success: function (res) {
                                if (!res || res.errcode != 0) {
                                    layer.msg(res ? res.description : "请求出错!");
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
});