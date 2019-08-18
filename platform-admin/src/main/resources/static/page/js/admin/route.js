var vm = new Vue({
    el: "#container",
    data: {
        tourId: "",
        id:"",
        name: "",
        day: "",
        thumb: "",
        routeSubtitle: "",
        routeIntroduce: "",
        thumb_result: "上传中...",
        img: "",
        img_result: "上传中...",
        money: "",
        tourists: [],
        sel_name:"",
        dictionaryId:"",
        dictionaryIds:[],
        dictionarys:[],
        end_name:"",
        sel_dictionaryId:""
    },
    methods: {
        formarDate: function () {

        },
         delSpec: function (index) {
                    this.dictionaryIds.splice(index, 1);
                },

        clear_path: function (index) {
            if (index == 1) {
                vm.thumb = "";
            } else if (index == 2) {
                vm.img = "";
            }
        }
    }
});

layui.use(['table', 'layer', "jquery", "form", "upload", "layedit"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var layedit = layui.layedit;

    $.ajax({
        url: "/admin/End/findAll",
        method: "GET",
        data: {
            page: 1,
            size: 10000
        },
        success: function(res) {
            if(!res || res.errCode != 0) {
                layer.msg(res ? res.errMsg : "请求出错!");
                return;
            }

            vm.tourists = res.data.content;
        }
    })

 $.ajax({
        url: "/admin/Dictionary/findAll",
        method: "GET",
        data: {
            page: 1,
            size: 10000
        },
        success: function(res) {
            if(!res || res.errCode != 0) {
                layer.msg(res ? res.errMsg : "请求出错!");
                return;
            }

            vm.dictionarys = res.data.content;
        }
    })

 $.ajax({
        url: "/admin/Dictionary/findAll",
        method: "GET",
        data: {
            page: 1,
            size: 10000
        },
        success: function(res) {
            if(!res || res.errCode != 0) {
                layer.msg(res ? res.errMsg : "请求出错!");
                return;
            }

            vm.dictionarys = res.data.content;
            console.log("dictionarys>>>>>>",vm.routes)
        }
    })

    $(".update").click(function (e) {
            console.log(">>>>>", vm.dictionaryId);
            if(vm.dictionaryId == "-1" ) {
                layer.msg("栏目不能为空!");
                return;
            }
            var now_dir = vm.dictionarys[vm.dictionaryId];
            for(var val of vm.dictionaryIds) {
                if(val.dictionaryId == now_dir.dictionaryId) {
                    layer.msg("不可重复添加！");
                    return;
                }
            }
            vm.dictionaryIds.push(now_dir);
            vm.dictionaryId = "-1";
            return false;
        });

    $("#message_info").submit(function (e) {
        var ids = vm.dictionaryIds.map(item => {
            return item.dictionaryId;
        })
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            dataType: "json",
            url: "/admin/Route/save",
            method: "POST",
            data: JSON.stringify({
                "endId": vm.tourId,
                "routeId":vm.id,
                "routeIntroduce": vm.routeIntroduce,
                "routeSubtitle": vm.routeSubtitle,
                "routeTitle": vm.name,
                "routeUrl": vm.img,
                "frontUrl": vm.thumb,
                "routeDay": vm.day,
                "Ids": ids.join(","),
                "routeBudget": vm.money
            }),
            success: function (res) {
                if (!res || res.errCode != 0) {
                    layer.msg(res ? res.errMsg : "请求出错!");
                    return;
                }
                vm.id = res.id;
                location.reload();
            }
        });
        return false;
    });

    form.on("switch(status)", function (obj) {
        var checked = obj.elem.checked;
        var routeId = obj.value;
        var status = checked ? 1 : 0;
        $.ajax({
            url: "/admin/Route/onFire",
            method: "GET",
            data: {
                routeId,
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
    form.on("switch(recommend)", function (obj) {
        var checked = obj.elem.checked;
        var routeId = obj.value;

        var status = checked ? 1 : 0;
        $.ajax({
            url: "/admin/Route/onRecommend",
            method: "GET",
            data: {
                routeId,
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
    })

    upload.render({
        elem: "#detail_img",
        url: "/file/upload",
        accept: "images",
        exts: "jpg|png|gif|bmp|jpeg",
        size: 1 * 1024,
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.thumb = result
            });
        },
        done: function (res) {
            if (res && res.errCode == 0) {
                vm.thumb_result = "上传成功!";
                vm.thumb = res.data.file;
            } else {
                vm.thumb_result = "上传失败!!!";
            }
        }
    })

    upload.render({
        elem: "#location_img",
        url: "/file/upload",
        accept: "images",
        exts: "jpg|png|gif|bmp|jpeg",
        size: 1 * 1024,
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.img = result
            });
        },
        done: function (res) {
            if (res && res.errCode == 0) {
                vm.img_result = "上传成功!";
                vm.img = res.data.file;
            } else {
                vm.img_result = "上传失败!!!";
            }
        }
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

    $("#select_role").submit(function (e) {
      console.log(">>>>>>sel_dictionaryId", vm.sel_dictionaryId);
        table.reload("manager_list", {
            page: {
                curr: 1
            },
            where: {
                name: vm.sel_name,
                endName:vm.end_name,
                dictionaryId:vm.sel_dictionaryId,
            }
        })

        return false;
    })

    table.render({
        elem: '#route_list'
        , url: '/admin/Route/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'route_title', title: '路线名称' },
            { field: "end_name", title: '所属目的地' },
            { toolbar: "#image_item", title: '封面图' },
            { toolbar: "#location_thumb", title: '路线图' },
            { field: 'create_time', title: '创建时间' },
            { toolbar: "#status", title: '启用状态' },
            { toolbar: "#recommend", title: '是否推荐' },
            { title: '操作', align: "center", width: 200, toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.code != 0) {
                layer.msg(res ? res.errMsg : "请求出错!");
                return {};
            }

            return {
                "code": res.code,
                "data": res.data.content,
                "count": res.data.totalElements,
                "limit": 15
            }
        }
    });
    table.on("tool(feedbacks)", function (obj) {
        var data = obj.data;
        console.log(">>>>data",data)
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/Route/delete/" + data.route_id,
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
                    url: "/admin/Route/findOne",
                    method: "GET",
                    data: {
                        routeId: data.route_id
                    },
                    success: function (res) {
                        if (res.errCode != 0) {
                            layer.msg(res ? res.errMsg : "请求出错");
                            return;
                        }
                        var ids=res.data.ids.split(",");
                        for(j = 0; j < ids.length; j++) {
                            for(i = 0; i < vm.dictionarys.length; i++) {
                                     if(ids[j]==vm.dictionarys[i].dictionaryId){
                                     vm.dictionaryIds.push(vm.dictionarys[i]);
                                     }
                          }
                        }

                        var info = res.data;
                        vm.name=info.routeTitle;
                        vm.id=info.routeId;
                        vm.tourId=info.endId;
                        vm.routeIntroduce=info.routeIntroduce;
                        vm.routeSubtitle=info.routeSubtitle;
                        vm.img=info.routeUrl;
                        vm.thumb=info.frontUrl;
                        vm.day=info.routeDay;
                        vm.money=info.routeBudget;
                        vm.thumb_result="已上传";
                        vm.img_result="已上传";
                        form.render("select");
                        layer.open({
                            title: `详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "500px"],
                        });


                    }
                })
                break;
        }
    });

    var edit_content;
    $(".modal").click(function () {
        vm.tourId = "";
        vm.id = "";
        vm.name =  "";
        vm.day =  "";
        vm.thumb =  "";
        vm.routeSubtitle =  "",
        vm.routeIntroduce =  "",
        vm.thumb_result =  "上传中...",
        vm.img =  "",
        vm.img_result =  "上传中...",
        vm.money = "",

        layer.open({
            title: "添加路线",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        });
        form.render("select");
    })
});