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
        tid: "",
        detail_result: "未上传",
        logo_result: "未上传",
        upload_result: "未上传",
        videos: [],
        video_img: "",
        video_img_result: "未上传",
        labels: [],
        provinces: [],
        citys: [],
        districts: [],
        tourists: []
    },
    methods: {
        delSpec: function (index) {
            this.specs.splice(index, 1);
        },
        delVideo: function (index) {
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
            } else if (index == 4) {
                this.video_img = "";
                this.video_img_result = "未上传";
            }
        }
    }
})

layui.use(['table', 'layer', "jquery", "upload", "form", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var upload = layui.upload;
    var layedit = layui.layedit;
    var $ = layui.jquery;

    $.ajax({
        url: "/activity/admin/hotal/touristList",
        success: function(res) {
            if(!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错");
                return;
            }
            vm.tourists = res.data.tourists;
        }
    })

    upload.render({
        elem: "#more_img",
        url: "/activity/admin/publicImg",
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
                $(".detail_delete").bind("click", function () {
                    for (var i in files) delete files[i];
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
                vm.upload_result = res.description || "上传失败";
            }
        }
    });

    upload.render({
        elem: "#detail_img",
        url: "/activity/admin/publicImg",
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
        url: "/activity/admin/publicImg",
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

    $("#hotal_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }

        data.logo = vm.detailImg;
        data.imgs = JSON.stringify(vm.imgs);
        data.thumb = vm.logoImg;
        data.specs = JSON.stringify(vm.specs);
        $.ajax({
            url: "/activity/admin/hotal/hotalAdd",
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
        var data = {
            name: res.field.name,
            price: res.field.price
        };
        vm.specs.push(data);

        layer.close(specWindow);
        return false;
    });

    $("#select_goods").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("hotal_list", {
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
        elem: '#hotal_list'
        , url: '/activity/admin/hotal/hotalList'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "hotal_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'name', align: "center", title: '酒店名称' },
            { field: 'price', width: 100, align: "center", title: '市场价' },
            { title: "景点缩略图", width: 180, align: "center", toolbar: "#goods_logo" },
            { field: 'spec', align: "center", title: '可选规格' },
            { field: 'stock', align: "center", width: 80, title: '房间总量' },
            { field: 'sold', align: "center", width: 80, title: '销量' },
            { align: "center", width: 100, title: "上架状态", toolbar: "#use_status" },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!!");
                return {};
            }
            var tourists = res.data.tourists;
            if (tourists.length != 0) {
                tourists = tourists.map(good => {
                    if(good.spec.length == 0) return good;
                    good.spec = good.spec.map(spec => {
                        return spec.name + "/" + spec.price;
                    }).join(",");

                    return good;
                });
            }
            return {
                "code": res.errcode,
                "data": tourists,
                "count": res.total,
                "limit": 15
            }
        }
    });

    form.on("switch(used)", function (obj) {
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

    table.on("tool(hotals)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/hotal/hotalDel",
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
                $.ajax({
                    url: "/activity/admin/hotal/hotalDetail",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (!res || res.errcode != 0) {
                            layer.msg(res ? res.description : "请求出错!");
                            return;
                        }

                        let info = res.data.info;
                        $("input[name=id]").val(info._id);
                        $("input[name=name]").val(info.name);
                        $("input[name=price]").val(info.price);
                        $("input[name=address]").val(info.address);
                        $("input[name=sold]").val(info.sold);
                        $("input[name=stock]").val(info.stock);
                        $("input[name=content]").val(info.content);
                        $("input[name=remark]").val(info.remark);
                        $("input[name=express_price]").val(info.express_price || 0);
                        vm.specs = info.spec;
                        vm.tid = info.tid;

                        vm.logoImg = info.logo;
                        vm.detailImg = info.thumb;
                        vm.detail_result = "上传成功";
                        vm.logo_result = "上传成功";

                        vm.imgs = info.pics;
                        vm.preImgs = vm.imgs;
                        vm.upload_result = "上传成功";

                        layer.open({
                            title: "编辑酒店",
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["800px", "550px"]
                        });

                        form.render("select");
                    }
                })
                break;
        }
    });

    $(".modal").click(function () {
        vm.imgs = [];
        vm.preImgs = [];
        vm.videos = [];
        vm.video_img = "";
        vm.logoImg = "";
        vm.detailImg = "";
        vm.specs = [];
        vm.provinces = [];
        vm.citys = [];
        vm.districts = [];

        layer.open({
            title: "添加酒店",
            type: 1,
            content: $(".add-subcat")
        });

        

        get_provinces().then(res => {
            if(!res || res.errcode != 0) {
                layer.msg(res ? res.description : "");
                return;
            }
            vm.provinces = res.data.provinces;
            get_citys({
                 value: vm.provinces[0]._id
            }).then(res => {
                if(!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "");
                    return;
                }
                vm.citys = res.data.citys;
                get_districts({
                    value: vm.citys[0]._id
                }).then(res => {
                    if(!res || res.errcode != 0) {
                        layer.msg(res ? res.description : "");
                        return;
                    }
                    vm.districts = res.data.districts;
                    setTimeout(function() {
                        form.render("select");
                    }, 500);
                    
                });
            });
        });

        layedit.set({
            height: 600,
            uploadImage: {
                url: "/activity/admin/publicImg",
                type: "POST"
            }
        })
        edit_content = layedit.build("content");
    });

    function get_provinces() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: "/activity/admin/hotal/prinvinces",
                method: "get",
                success: resolve,
                fail: reject
            })
        })
    }

    function get_citys(obj) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: "/activity/admin/hotal/citys",
                method: "get",
                data: {
                    pid: obj.value
                },
                success: resolve,
                fail: reject
            });
        })
    }

    function get_districts(obj) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: "/activity/admin/hotal/districts",
                method: "get",
                data: {
                    cid: obj.value
                },
                success: resolve,
                fail: reject
            });
        })
    }

    form.on("select(province)", function(obj) {
        vm.citys = [];
        get_citys(obj).then(res => {
            if(!res || res.errcode != 0) {
                layer.msg(res ? res.description : "");
                return;
            }
            vm.citys = res.data.citys;
            vm.districts = [];
            get_districts({
                value: res.data.citys[0]._id
            }).then(res => {
                if(!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "");
                    return;
                }
                vm.districts = res.data.districts;
                setTimeout(function() {
                    form.render("select");
                }, 500);
            });
        })
    });

    form.on("select(city)", function(obj) {
        vm.districts = [];
        get_districts(obj).then(res => {
            if(!res || res.errcode != 0) {
                layer.msg(res ? res.description : "");
                return;
            }
            vm.districts = res.data.districts;
            setTimeout(function() {
                form.render("select");
            }, 500);
        })
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