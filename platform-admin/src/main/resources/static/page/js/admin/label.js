var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
        logo_img: "",
        logo_result: "上传中...",
        imgs: [],
        preImgs: [],
        upload_result: "上传中..."
    },
    methods: {
        clear_path: function(index) {
            if(index == 1) {
                this.logo_img = "";
                this.logo_result = "上传中...";
            }else if(index == 2) {
                this.imgs = [];
                this.preImgs = [];
                this.upload = "上传中..."
            }
        }
    }
});

layui.use(['table', 'layer', "jquery", "upload"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    upload.render({
        elem: '#logo_img',
        url: "/?action=admin.upload_img",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.logo_img = result;
            });
        },
        done: function (res) {
            if (res && res.errcode == 0) {
                vm.logo_result = "上传成功!";
                vm.logo_img = res.data.src;
            } else {
                vm.logo_result = "上传失败!!!";
            }
        }
    });

    upload.render({
        elem: "#more_img",
        url: "/?action=admin.upload_img",
        auto: false,
        accept: "images",
        exts: "jpg|png|gif|bmp|jpeg",
        size: 1 * 1024,
        number: 5,
        multiple: true,
        choose: function(obj) {
            var files = obj.pushFile();
            obj.preview(function (index, file, result) {
                vm.preImgs.push(result);
                $(".detail_delete").bind("click", function(){
                    for(var i in files) delete files[i];
                })
            });
        },
        bindAction: "#upload",
        done: function (res) {
            vm.imgs.push(res.data.src);
        },
        allDone: function (obj) {
            if (obj.total == obj.successful) {
                vm.upload_result = "上传成功!";
                vm.preImgs = vm.imgs;
            } else {
                vm.upload_result =  res.description || "上传失败";
            }
        }
    })

    $("#label_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        data.imgs = vm.imgs.join(",");
        data.thumb = vm.logo_img;
        $.ajax({
            url: "/?action=admin.shop.label_add",
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

    table.render({
        elem: '#label_list'
        , url: '/?action=admin.shop.label_list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "label_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'name', width: 180, title: '分类名称' },
            { title: 'LOGO', toolbar: '#logo_item' },
            { field: 'position', width: 180, title: '排序' },
            { width: 180, title: '启用状态', toolbar: "#use_status" },
            { width: 180, title: '主页功能栏', toolbar: "#main_status" },
            { title: '操作', fixed: "right", width: 180, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return;
            }
            return {
                "code": res.errcode,
                "data": res.labels,
                "count": res.total,
                "limit": 15
            }
        }
    });

    table.on("tool(labels)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/?action=admin.shop.label_del",
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
                    url: "/?action=admin.user.label_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        var labelInfo = res.labelInfo;
                        $("input[name=realname]").val(labelInfo.nickname);
                        $("input[name=mobile]").val(labelInfo.mobile);
                        $("input[name=id]").val(labelInfo._id);
                        action = "edit";
                        form.render("select");
                        layer.open({
                            title: `${data.nickname}的详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "500px"],
                        });
                    }
                })
                break;
        }
    });

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.shop.label_state",
            method: "GET",
            data: {
                checked,
                id
            },
            success: function (res) {
                if(res.errcode != 0) {
                    layer.msg(res.description);
                    return;
                }
                layer.msg("操作成功!");
            }
        })
    });

    form.on("switch(labeled)", function(obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.shop.label_main",
            method: "GET",
            data: {
                checked,
                id
            },
            success: function (res) {
                if(res.errcode != 0) {
                    layer.msg(res.description);
                    return;
                }
                layer.msg("操作成功!");
            }
        })
    });

    $(".modal").click(function () {
        $("#label_info").get(0).reset();

        action = "add";
        layer.open({
            title: "添加角色",
            type: 1,
            content: $(".add-subcat"),
            area: ["800px", "650px"],
        })
    })

    $(document).on("click", ".layui-upload-img", function (e) {
        layer.open({
            type: 1,
            shade: true,
            title: false,
            area: ["400px", "400px"],
            content: "<img src='" + $(this).attr("src") + "' style='display: block;width:100%;height: 100%;max-width:100%'>"
        });
    });
});