var vm = new Vue({
    el: "#courses",
    data: {
        name: "",
        author: "",
        press: "",
        content: "",
        couriseId: "",
        courises: [],
        thumb: "",
        upload_result: "上传中..."
    },
    methods: {
        clear_path: function () {
            this.thumb = "";
            this.upload_result = "上传中...";
        }
    }
});
layui.use(["table", "jquery", "layer", "form", "upload"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    var upload = layui.upload;

    $.ajax({
        url: "/activity/admin/couriseList",
        data: {
            page: 1
        },
        success: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "参数有误!");
                return;
            }
            vm.courises = res.data.courises;
        }
    });

    upload.render({
        elem: "#thumb",
        url: "/activity/admin/publicImg",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.thumb = result;
            });
        },
        done: function (res) {
            if (res && res.errcode == 0) {
                vm.upload_result = "上传成功!";
                vm.thumb = res.data.src;
            } else {
                vm.upload_result = "上传失败!!!";
            }
        }
    });

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;

        checked = checked ? 1 : 0;
        $.ajax({
            url: "/activity/admin/courBookState",
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

    table.render({
        elem: "#book_list",
        url: "/activity/admin/courBookList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "book_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'name', align: "center", title: "书籍名称" },
            { title: "缩略图", align: "center", toolbar: "#user_logo" },
            { field: 'author', align: "center", title: '作者' },
            { field: 'press', align: "center", title: '出版社' },
            { field: 'content', align: "center", title: '图书简介' },
            { field: 'couriseName', align: "center", title: '所属课程' },
            { toolbar: '#status', align: "center", title: "启用状态" },
            { title: '操作', align: "center", toolbar: "#operator_item" },
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            var books = res.data.books.map(book => {
                book.couriseName = book.courseId ? book.courseId.name : "已删除";
                return book;
            })
            return {
                "code": res.errcode,
                "data": books,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    $(".modal").click(function () {
        vm.name = "";
        vm.mobile = "";

        form.render("select");
        layer.open({
            title: "添加图书",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "550px"]
        });
    })

    table.on("tool(books)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/courBookDel",
                            data: {
                                id: data._id
                            },
                            success: function (res) {
                                if (!res || res.errcode != 0) {
                                    layer.msg(res ? res.description : "请求失败!!!");
                                    return;
                                }
                                location.reload();
                            }
                        });
                        return true;
                    }
                });
                break;
            case "detail":
                $.ajax({
                    url: "/activity/admin/courBookDetail",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (!res || res.errcode != 0) {
                            layer.msg(res ? res.description : "请求失败!!!");
                            return;
                        }

                        var info = res.data.info;
                        vm.name = info.name;
                        vm.author = info.author;
                        vm.press = info.press;
                        vm.content = info.content;
                        vm.couriseId = info.courseId;
                        vm.thumb = info.thumb;

                        layer.open({
                            title: `${info.name}的详情`,
                            content: $(".add-subcat"),
                            type: 1,
                            area: ["700px", "550px"]
                        });
                        form.render("select");
                    }
                })
                break;
        }
    });

    $("#book_info").submit(function (e) {
        $.ajax({
            url: "/activity/admin/courBookAdd",
            method: "POST",
            data: {
                name: vm.name,
                author: vm.author,
                press: vm.press,
                content: vm.content,
                couriseId: vm.couriseId,
                thumb: vm.thumb
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