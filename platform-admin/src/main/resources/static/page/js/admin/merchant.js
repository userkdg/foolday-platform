var vm = new Vue({
    el: "#container",
    data: {
        merchantId: "",
        merchantName: "",
        merchantDetail: "",
        location: "",
        lat: "",
        lng: "",
        merchantStatus: "",
        logo_result: "上传中....",
        average: "",
        detailImg: "",
        content: "",
        contents: [],
        sel_name:"",
        dictionaryName:[]
    },
    methods: {
        formarDate: function () {
        },

        clear_path: function (index) {
            if (index == 1) {
                vm.detailImg = "";
            }
        },
        delSpec: function (index) {
            this.contents.splice(index, 1);
        },
//        seachByLoc: function() {
//            if(!location) {
//                layer.msg("商家地址不能为空!");
//                return;
//            }
//            codeAddress(vm.location);
//        },
        loc_callback: function(result) {
            console.log(">>>>>callback", result);
            vm.lat = result.location.lat;
            vm.lng = result.location.lng;
        }
    }
});

layui.use(['table', 'layer', "jquery", "form", "upload", "layedit"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var layedit = layui.layedit;

    init();
    $("#seachByLoc").click(function(e) {
        if(!vm.location) {
             layer.msg("商家地址不能为空!");
             return false;
         }
         codeAddress(vm.location);
         return false;
    })

  $(document).on("click", ".layui-upload-img", function (e) {
        layer.open({
            type: 1,
            shade: true,
            title: false,
            area: ["600px", "350px"],
            content: "<img src='" + $(this).attr("src") + "' style='display: block;width:100%;height: 100%;max-width:100%'>"
        });
    });
    var edit_detail;
    $("#message_info").submit(function (e) {
        vm.merchantDetail = layedit.getContent(edit_detail)
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            dataType: "json",
            url: "/admin/Merchant/save",
            method: "POST",
            data: JSON.stringify({
                   "merchantId":vm.merchantId,
                "merchantName": vm.merchantName,
                "merchantDetail": vm.merchantDetail,
                "merchantUrl": vm.detailImg,
                "merchantEw": vm.lat,
                "merchantSn": vm.lng,
                "merchantStatus":0,
                "preferentialContent": vm.contents.join(","),
                "average": vm.average
            }),
            success: function (res) {
                vm.id = res.id;
                if (res.errCode != 0) {
                    layer.msg(res.description);
                    return;
                }
                location.reload();
            }
        });
        return false;
    });

    form.on('submit(add_spec)', function (res) {
        vm.contents.push(vm.content);
        layer.close(specWindow);
        vm.content = "";
        return false;
    });

    $("#select_role").submit(function (e) {

        table.reload("manager_list", {
            page: {
                curr: 1
            },
            where: {
                name: vm.sel_name
            }
        })

        return false;
    })
    upload.render({
        elem: "#detail_img",
        url: "/file/upload",
        accept: "images",
        exts: "jpg|png|gif|bmp|jpeg",
        size: 1 * 1024,
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.detailImg = result
            });
        },
        done: function (res) {
            console.log(">>>upload result", res)
            if (res && res.errCode == 0) {
                vm.logo_result = "上传成功!";
                vm.detailImg = res.data.file;
            } else {
                vm.logo_result = "上传失败!!!";
            }
        }
    })
    table.render({
        elem: '#route_list'
        , url: '/admin/Merchant/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            //  { field: '#', title: '所属打卡点id' },
            { field: 'merchantName', title: '商家名称' },
            { field: 'merchantDetail', title: '商家详情' },
            { toolbar: '#image_item', title: '商家图' },
            { field: 'merchantEw', title: '商家经度' },
            { field: 'merchantSn', title: '商家纬度' },
            { toolbar: "#status", title: '启用状态' },
            { field: 'preferentialContent', title: '优惠内容' },
            { field: 'average', title: '人均消费' },
            { field: 'createTime', title: '添加时间' },
            { title: '操作', align: "center", width: 200, toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errCode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.code,
                "data": res.data.content,
                "count": res.data.totalElements,
                "limit": 18
            }
        }
    });
    form.on("switch(status)", function (obj) {
        var checked = obj.elem.checked;
        var merchantId = obj.value;
        console.log(checked);
        var status = checked ? 1 : 0;
        $.ajax({
            url: "/admin/Merchant/onFire",
            method: "GET",
            data: {
                merchantId,
                status
            },
            success: function (res) {
                if (!res || res.errCode != 0) {
                    layer.msg("请求出错！！");
                    return;
                }
                layer.msg("操作成功");
            }
        })
    });

    table.on("tool(test)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/Merchant/delete/" + data.merchantId,
                            //                            data: {
                            //                                id: data._id
                            //                            },
                            success: function (res) {
                                if (res.errCode != 0) {
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
                    url: "/admin/Merchant/findOne/"+data.merchantId,
                    method: "GET",
                    dataType: "json",
                    success: function (res) {
                        console.log(">>>res", res);
                        if (!res || res.errCode != 0) {
                            layer.msg(res ? res.errMsg : "请求出错");
                            return;
                        }
                        var info = res.data;
                       vm.average = res.data.average;
                       vm.merchantId = info.merchantId;
                       vm.merchantName = info.merchantName;
                       vm.merchantDetail = info.merchantDetail;
                       vm.detailImg = info.detailImg;
                       vm.lat = info.merchantSn;
                       vm.lng = info.merchantEw;
                       vm.merchantStatus = info.merchantStatus;
                       vm.contents = info.preferentialContent.split(",");
                       vm.average = info.average;
                        vm.detailImg = info.merchantUrl;
                        vm.logo_result="上传成功";

                        index = layer.open({
                            title: `详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "500px"],
                        });
                        layer.full(index);
                        form.render("select");

                        layedit.set({
                            height: 600,
                            uploadImage: {
                                url: "/file/layedit/upload",
                                type: "POST"
                            }
                        })
                        edit_detail = layedit.build("merchantDetail");
                        layedit.sync(edit_detail);
                    }
                })
                break;
        }
    });

    var index
    $(".modal").click(function () {
        index = layer.open({
            title: "添加",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        });
        layer.full(index);

        layedit.set({
            height: 600,
            uploadImage: {
                url: "/file/layedit/upload",
                type: "POST"
            }
        })
        edit_detail = layedit.build("merchantDetail");
    });

    var specWindow;
    $("#add_spec").click(function() {
        specWindow = layer.open({
            title: "添加优惠内容",
            content: $(".add-spec"),
            type: 1,
            area: ["650px", "300px"]
        })
    });

    $(".update").click(function() {
        var loading = layer.load();
        $.ajax({
            url: "/admin/Merchant/flush",
            success: function(res) {
                if(!res || res.errCode != 0) {
                    layer.msg(res ? res.errMsg : "请求有误!");
                    return;
                }

                layer.msg("刷新成功!");
                layer.close(loading);
            }
        })
    })
});