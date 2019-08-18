var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
        specs: [],
        stock: 0,
        price: 0,
        logoImg: "",
        detailImg: "",
        preImgs: [],
        imgs: [],
        detail_result: "未上传",
        logo_result: "未上传",
        upload_result: "未上传",
        videos: [],
        video_img: "",
        video_img_result: "未上传",
        labels: []
    },
    methods: {
        delSpec: function (index) {
            this.specs.splice(index, 1);
        },
        delVideo: function(index) {
            this.videos.splice(index, 1);
        },
        clear_path: function (index) {
            if (index == 1) {
                this.detailImg = "";
                this.detail_result = "未上传";
            } else if (index == 2) {
                this.logoImg = "";
                this.logo_result = "未上传";
            } else if (index == 3) {
                this.imgs = [];
                this.preImgs = [];
                this.upload_result = "未上传";
            }else if(index == 4) {
                this.video_img = "";
                this.video_img_result = "未上传";
            }
        }
    }
})

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var upload = layui.upload;
    var layedit = layui.layedit;

    //获得商品类别
    $.ajax({
        url: "/?action=admin.shop.labels",
        method: "GET",
        success: function(res) {
            if(res.labels.length != 0) {
                vm.labels = res.labels;
            }
        }
    })

    upload.render({
        elem: "#more_img",
        url: "/?action=admin.upload_img",
        auto: false,
        accept: "images",
        exts: "jpg|png|gif|bmp|jpeg",
        size: 1 * 1024,
        number: 5,
        multiple: true,
        choose: function (obj) {
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
    });

    upload.render({
        elem: "#detail_img",
        url: "/?action=admin.upload_img",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#detailImg").attr("src", result);
            });
        },
        done: function (res) {
            if (res && res.errcode == 0) {
                vm.detail_result = "上传成功!";
                vm.detailImg = res.data.src;
            } else {
                vm.detail_result = "上传失败!!!";
            }
        }
    });

    upload.render({
        elem: "#logo",
        url: "/?action=admin.upload_img",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#logoImg").attr("src", result);
            });
        },
        done: function (res) {
            if (res && res.errcode == 0) {
                vm.logo_result = "上传成功!";
                vm.logoImg = res.data.src;
            } else {
                vm.logo_result = "上传失败!!!";
            }
        }
    });

    //添加视频
    var videoWindow;
    $("#add_video").click(function () {
        $("#video_info").get(0).reset();
        videoWindow = layer.open({
            title: "添加视频",
            type: 1,
            content: $(".add-videos"),
            area: ["500px", "300px"]
        })
    });
    form.on('submit(add_video)', function (res) {
        if(!vm.video_img) {
            layer.msg("请上传视频缩略图");
            return false;
        }
        let data = {
            title: res.field.video_title,
            url: res.field.video_url,
            img: vm.video_img
        }
        vm.videos.push(data);

        layer.close(videoWindow);
        return false;
    });
    upload.render({
        elem: "#video_img",
        url: "/?action=admin.upload_img",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#videoImg").attr("src", result);
            });
        },
        done: function (res) {
            if (res && res.errcode == 0) {
                vm.video_img_result = "上传成功!";
                vm.video_img = res.data.src;
            } else {
                vm.video_img_result = "上传失败!!!";
            }
        }
    });
    
    $("#goods_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        
        data.logo = vm.detailImg;
        data.pics = vm.imgs.join(",");
        data.thumb = vm.logoImg;
        data.specs = JSON.stringify(vm.specs);
        data.videos = JSON.stringify(vm.videos);
        data.content = layedit.getContent(edit_content);

        if (action == "add") {
            action = "admin.shop.goods_add";
        } else if (action == "edit") {
            action = "admin.shop.goods_edit";
        }
        $.ajax({
            url: "/?action=" + action,
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

    //添加规格
    var specWindow;
    $("#add_spec").click(function () {
        $("#spec_info").get(0).reset();
        specWindow = layer.open({
            title: "添加规格",
            type: 1,
            content: $(".add-spec"),
            area: ["500px", "300px"]
        })
    });
    form.on('submit(add_spec)', function (res) {
        let data = {
            name: res.field.name,
            price: res.field.price
        };
        vm.specs.push(data);

        layer.close(specWindow);
        return false;
    });

    $("#select_goods").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("goods_list", {
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
        elem: '#goods_list'
        , url: '/?action=admin.shop.goods_list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "goods_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'title', width: 100, align: "center", title: '商品标题' },
            { field: 'label', width: 100, align: "center", title: '所属分类' },
            { title: "商品缩略图", width: 180, align: "center", toolbar: "#goods_logo" },
            { field: 'price', width: 100, align: "center", title: '商品单价' },
            { field: 'express_price', width: 100, align: "center", title: '快递运费'},
            { field: 'specs', title: '商品规格' },
            { field: 'stock', align: "center", width: 80, title: '库存量' },
            { field: 'sold', align: "center", width: 80, title: '销量' },
            { align: "center", width: 100, title: "上架状态", toolbar: "#use_status"},
            { title: '操作', fixed: "right", width: 210, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res.description || "请求出错!!!");
                return {};
            }
            res.goods = res.goods.map(good => {
                good.specs = good.specs.map(spec => {
                    return spec.name + "/" + spec.price;
                }).join(",");
                good.label = good.label.name;
                return good;
            })
            return {
                "code": res.errcode,
                "data": res.goods,
                "count": res.total,
                "limit": 15
            }
        }
    });

    form.on("switch(used)", function(obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.shop.goods_status",
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
    })

    table.on("tool(goods)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/?action=admin.shop.goods_delete",
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
                    url: "/?action=admin.shop.goods_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        $("input[name=id]").val(res.goodsInfo._id);
                        $("input[name=title]").val(res.goodsInfo.title);
                        $("input[name=price]").val(res.goodsInfo.price);
                        $("input[name=sold]").val(res.goodsInfo.sold);
                        $("input[name=stock]").val(res.goodsInfo.stock);
                        $("input[name=express_price]").val(res.goodsInfo.express_price || 0);
                        vm.specs = res.goodsInfo.specs;

                        vm.logoImg =  res.goodsInfo.logo;
                        vm.detailImg = res.goodsInfo.thumb;
                        vm.detail_result = "上传成功";
                        vm.logo_result = "上传成功";

                        vm.imgs = res.goodsInfo.pics;
                        vm.preImgs = vm.imgs;
                        vm.upload_result = "上传成功";

                        $("#content").val(res.goodsInfo.content);
                        vm.videos = res.goodsInfo.videos;

                        action = "edit";
                        var layer_window = layer.open({
                            title: "编辑商品",
                            type: 1,
                            content: $(".add-subcat")
                        });
                        layer.full(layer_window);

                        layedit.set({
                            height: 600,
                            uploadImage: {
                                url: "/?action=admin.upload_img",
                                type: "POST"
                            }
                        })
                        edit_content = layedit.build("content");
                    }
                })
                break;
            case "detail":
                $.ajax({
                    url: "/?action=admin.shop.goods_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        res.goodsInfo.specs = res.goodsInfo.specs.map(spec => {
                            return spec.name + "/" + spec.price;
                        }).join("，");
                        $("input[name=detail_title]").val(res.goodsInfo.title);
                        $("input[name=detail_price]").val(res.goodsInfo.title);
                        $("input[name=detail_specs]").val(res.goodsInfo.specs);
                        $("input[name=detail_sold]").val(res.goodsInfo.sold);
                        $("input[name=detail_stock]").val(res.goodsInfo.stock);
                        $("#detail_big_img").attr("src", res.goodsInfo.logo);
                        $("#detail_logoImg").attr("src", res.goodsInfo.thumb);

                        var imgs = "";
                        for (var img of res.goodsInfo.pics) {
                            imgs += '<img src="' + img + '" class="layui-upload-img">'
                        }
                        $("#detail_imgs").html(imgs);

                        var layer_window = layer.open({
                            title: "商品详情",
                            type: 1,
                            content: $(".goods-detail")
                        });
                        layer.full(layer_window);

                        $("#detail_content").val(res.goodsInfo.content);
                        layedit.set({
                            height: 600
                        })
                        layedit.build("detail_content");
                    }
                })
                break;
        }
    });

    var edit_content;
    $(".modal").click(function () {
        $("#goods_info").get(0).reset();
        vm.imgs = [];
        vm.preImgs = [];
        vm.videos = [];
        vm.video_img = "";
        vm.logoImg = "";
        vm.detailImg = "";
        vm.specs = [];
        $("input[name=id]").val("");

        action = "add";
        var open_view = layer.open({
            title: "添加商品",
            type: 1,
            content: $(".add-subcat")
        });
        layer.full(open_view);

        layedit.set({
            height: 600,
            uploadImage: {
                url: "/?action=admin.upload_img",
                type: "POST"
            }
        })
        edit_content = layedit.build("content");
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