var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
        // videos: [],
        dtype: 4,
        thumb: "",
        id: "",
        title: "",
        content: "",
        url: "",
        upload_result: "上传中...",
    },
    methods: {
        clear_video_thumb: function () {
            this.thumb_video = "";
            this.thumb_video_result = "上传中...";
        },
        clear_thumb: function () {
            this.thumb = "";
            this.upload_result = "上传中...";
        },
        delVideo: function (index) {
            this.videos.splice(index, 1);
        },

    }
});

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var layedit = layui.layedit;

    $("#product_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {
            action: "admin.system.introduces_add"
        };

        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        let dtype = parseInt(data.dtype);
        data.thumb = vm.thumb;
        if (dtype == 4) {
            let content = layedit.getContent(edit_content);
            data.content = content;
        }
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

    $("#video_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};

        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        data.thumb = vm.thumb_video;
        vm.videos.push(data);

        layer.close(window_video_add);
        return false;
    });

    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("product_list", {
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
        elem: "#video_img",
        url: "/?action=admin.upload_img",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.thumb_video = result;
            });
        },
        done: function (res) {
            if (res && res.errcode == 0) {
                vm.thumb_video_result = "上传成功!";
                vm.thumb_video = res.data.src;
            } else {
                vm.thumb_video_result = "上传失败!!!";
            }
        }
    })

    upload.render({
        elem: "#thumb_img",
        url: "/?action=admin.upload_img",
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
    })

    table.render({
        elem: '#product_list'
        , url: '/?action=admin.system.introduces'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "product_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'dtype', width: 180, title: '类型' },
            { field: 'title', title: '标题' },
            { field: 'content', title: '内容' },
            { title: "产品缩略图", width: 180, align: "center", toolbar: "#goods_logo" },
            { title: "启用状态", fixed: "right", width: 180, align: "center", toolbar: "#status" },
            { title: '操作', fixed: "right", width: 180, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }

            var introduces = res.introduces.map(item => {
                if (item.dtype == 3) {
                    item.dtype = "视频简介";
                    item.content = item.url;
                } else if (item.dtype == 4) {
                    item.dtype = "图文简介";
                }
                return item;
            })
            return {
                "code": res.errcode,
                "data": introduces,
                "count": res.total,
                "limit": 15
            }
        }
    });

    table.on("tool(products)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/?action=admin.system.introduces_del",
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
                action = "edit";
                $.ajax({
                    url: "/?action=admin.system.introduce_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        var product_info = res.info;
                        vm.id = product_info._id;
                        vm.title = product_info.title;
                        vm.content = product_info.content;
                        vm.url = product_info.url;
                        vm.dtype = product_info.dtype;
                        vm.thumb = product_info.thumb;
                        vm.upload_result = "上传成功";

                        window_add = layer.open({
                            title: `编辑${data.title}`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "400px"],
                        });
                        layer.full(window_add);

                        layedit.set({
                            height: 900,
                            uploadImage: {
                                url: "/?action=admin.upload_img",
                                type: "POST"
                            }
                        });
                        setTimeout(function () {
                            edit_content = layedit.build("content");
                        }, 500)
                    }
                })
                break;
        }
    });

    form.on("switch(status)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.system.introduces_status",
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
    });

    var edit_content;
    var window_add;
    $(".modal").click(function () {
        $("#product_info").get(0).reset();

        vm.id = "";
        vm.title = "";
        vm.content = "";
        vm.url = "";
        vm.dtype = 4;
        vm.thumb = "";
        vm.upload_result = "上传中...";
        action = "add";
        window_add = layer.open({
            title: "添加内容",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "400px"],
        });
        layer.full(window_add);

        layedit.set({
            height: 900,
            uploadImage: {
                url: "/?action=admin.upload_img",
                type: "POST"
            }
        });
        edit_content = layedit.build("content");
    })

    var window_video_add;
    // $("#add_video").click(function () {
    //     $("#video_info").get(0).reset();
    //     vm.thumb_video = "";
    //     vm.thumb_video_result = "上传中...";

    //     window_video_add = layer.open({
    //         title: "添加视频",
    //         type: 1,
    //         content: $(".add-videos"),
    //         area: ["650px", "500px"],
    //     });
    // });

    $("#dtype").bind("change", function () {
        var val = $(this).val();
        vm.dtype = val;
    });

    $(document).on("click", ".layui-upload-img", function (e) {
        layer.open({
            type: 1,
            shade: true,
            title: false,
            area: ["600px", "350px"],
            content: "<img src='" + $(this).attr("src") + "' style='display: block;width:100%;height: 100%;max-width:100%'>"
        });
    });
});