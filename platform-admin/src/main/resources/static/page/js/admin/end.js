var vm = new Vue({
    el: "#container",
    data: {
        endName: "",
        endTitle: "",
        endIntroduce: "",
        ninePicture:"",
        ninePlan:"",
        createTime: "",
        preImgs: [],        //选择图片成功
        imgs: [],           //上传成功
        endPin:"",
        upload_result: "上传中....",
        detailImg: "",
        logo_result: "上传中....",
        nine_result:"上传中",
        detail: "",
        remark: "5",
        location: "",
        lat: "",
        lng: "",
        sel_name: "",
        id:"",
        tourDetail: ""
    },
    methods: {
        formarDate: function () {

        },
        clear_path: function (index) {
            if (index == 1) {
                vm.detailImg = "";
            }else if(index == 3){
                vm.preImgs = [];
                vm.imgs = [];
            }else if(index==2){
            vm.ninePicture ="";
            }
        },
//        seachByLoc: function() {
//            console.log(">>>>>>", "1111111111");
//             if(!vm.location) {
//                 layer.msg("商家地址不能为空!");
//                 return;
//             }
//             codeAddress(vm.location);
//             return;
//         },
         loc_callback: function(result) {
             console.log(">>>>>>", result);
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
        console.log(">>>>", "22222222222")
        if(!vm.location) {
             layer.msg("地址不能为空!");
             return;
         }
         codeAddress(vm.location);
         return false;
    })

    upload.render({
        elem: "#more_img",
        url: "/file/upload",
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
            vm.imgs.push(res.data.file);
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
        url: "/file/upload",
        accept: "images",
        exts: "jpg|png|gif|bmp|jpeg",
        size: 1 * 1024,
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.detailImg = result.data.file;
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

     upload.render({
            elem: "#nine_picture",
            url: "/file/upload",
            accept: "images",
            exts: "jpg|png|gif|bmp|jpeg",
            size: 1 * 1024,
            before: function (obj) {
                obj.preview(function (index, file, result) {
                    vm.detailImg = result.data.file;
                });
            },
            done: function (res) {
                console.log(">>>upload result", res)
                if (res && res.errCode == 0) {
                    vm.nine_result = "上传成功!";
                    vm.ninePicture = res.data.file;
                } else {
                    vm.nine_result = "上传失败!!!";
                }
            }
        })


    $("#message_info").submit(function (e) {
    let imgs =  vm.preImgs.join(",");
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            dataType: "json",
            url: "/admin/End/save",
            method: "POST",
            data: JSON.stringify({
                "endId":vm.id,
                "endName": vm.endName,
                "endTitle": vm.endTitle,
                "endIntroduce": vm.endIntroduce,
                "ninePicture":vm.ninePicture,
                "endEw": vm.lat,
                "endSn": vm.lng,
                "ninePlan":vm.ninePlan,
                "endPin":vm.endPin,
                "endUrl": vm.detailImg,
                "endAddress":vm.location,
                "endCarousel": imgs,
                "endGrade": vm.remark,
                "endIntroduce": layedit.getContent(edit_detail)
            }) ,
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

    $("#select_tour").submit(function (e) {
        table.reload("tourist_list", {
            page: {
                curr: 1
            },
            where: {
                name: vm.sel_name
            }
        })

        return false;
    })

    table.render({
        elem: '#tourist_list'
        , url: '/admin/End/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "tourist_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'endName', title: '景区名称' },
            { field: "endTitle", title: '景区简介' },
            { field: 'endAddress', title: '所在地址' },
            { toolbar: '#image_item', title: '缩略图' },
            { field: 'endGrade', title: '星级评分' },
            { toolbar: "#recommend", title: '是否推荐' },
            { toolbar: "#status", title: '启用状态' },
            { field: 'createTime', title: '创建时间' },
            { title: '操作', align: "center", width: 200, toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (res.code != 0) {
                layer.msg(res.errMsg);
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
        var id = obj.value;
        var status = checked ? 1 : 0;
         console.log(status);
            $.ajax({
                url: "/admin/End/onFire/"+id+"/"+status,
                method: "GET",
                success: function (res) {
                    if (!res || res.errCode != 0) {
                        layer.msg("请求出错！！");
                        return;
                    }
                    layer.msg("操作成功");
                }
            })


    });

    form.on("switch(recommend)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        var status = checked ? 1 : 0;
        console.log(">>>status",status);
            $.ajax({
                url: "/admin/End/onRecommend/"+id+"/"+status,
                method: "GET",
                success: function (res) {
                    if (!res || res.errCode != 0) {
                        layer.msg("请求出错！！");
                        return;
                    }
                    layer.msg("操作成功");
                }
            })
    })
    table.on("tool(tourists)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/End/delete/" + data.endId,
                            //                            data: {
                            //                                adviceId: data.adviceId
                            //                            },
                            success: function (res) {
                                console.log(res);
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
                //根据ID获取当前记录的详情
                $.ajax({
                    url: "/admin/End/findOne",
                    method: "GET",
                    data: {
                        endId: data.endId
                    },
                    success: function (res) {
                        if (!res || res.errCode != 0) {
                            layer.msg(res ? res.errMsg : "请求出错");
                            return;
                        }
                        var info = res.data;
                         vm.endName = info.endName;
                         vm.id = info.endId;
                         vm.endName = info.endName;
                         vm.endTitle = info.endTitle;
                         vm.endIntroduce = info.endIntroduce;
                         vm.lat = info.endEw;
                         vm.lng = info.endSn;
                         vm.endPin=info.endPin;
                         vm.ninePlan=info.ninePlan;
                         vm.ninePicture=info.ninePicture;
                         vm.detailImg=info.detailImg;
                         vm.location=info.endAddress;
                         vm.preImgs = info.endCarousel.split(",");
                         vm.imgs = info.endCarousel.split(",");
                         vm.detailImg = info.endUrl;
                         vm.remark=info.endGrade;
                         vm.detail=info.endIntroduce;
                         vm.upload_result="上传成功";
                         vm.logo_result="上传成功";
                         vm.tourDetail = info.endIntroduce;

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
                        edit_detail = layedit.build("tourDetail");
                        layedit.sync(edit_detail);
                    }
                })
                break;
        }
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
    var edit_detail;
    var index;
    $(".modal").click(function () {
        index = layer.open({
            title: "添加景区",
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
        edit_detail = layedit.build("tourDetail");
    })
});