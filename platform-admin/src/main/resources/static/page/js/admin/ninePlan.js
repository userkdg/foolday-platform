var vm = new Vue({
    el: "#container",
    data: {
        id: "",
        ninePlan: "",
        ninePicture:"",
        logo_result:"上传中"
    },
    methods: {
        formarDate: function () {

        },
        clear_path: function (index) {
                    if (index == 1) {
                        vm.ninePicture = "";
                    }else if(index == 3){
                        vm.preImgs = [];
                        vm.imgs = [];
                    }else if(index==2){
                    vm.ninePicture ="";
                    }
                }
        }

});

layui.use(['table', 'layer', "jquery", "form","upload"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    init();


    $("#message_info").submit(function (e) {
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            url: "/admin/NinePlan/save",
            method: "POST",
            data: JSON.stringify({
                "id": vm.id,
                "ninePlan": vm.ninePlan,
                "ninePicture": vm.ninePicture
            }),
            success: function (res) {
                if (!res || res.errCode != 0) {
                    layer.msg(res ? res.errMsg : "请求出错!");
                    return;
                }
                location.reload();
            }
        });
        return false;
    });

        upload.render({
        elem: "#detail_img",
        url: "/file/upload",
        accept: "images",
        exts: "jpg|png|gif|bmp|jpeg",
        size: 1 * 1024,
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.ninePicture = result.data.file;
            });
        },
        done: function (res) {
            console.log(">>>upload result", res)
            if (res && res.errCode == 0) {
                vm.logo_result = "上传成功!";
                vm.ninePicture = res.data.file;
            } else {
                vm.logo_result = "上传失败!!!";
            }
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

    table.render({
        elem: '#card_list'
        , url: '/admin/NinePlan/findAll'
//        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,height: '600px'
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { toolbar: '#nine_image', title: '九图图片' },
            { field: 'ninePlan', title: '九图计划' },
            { title: '操作', align: "center", toolbar: "#operator_item" }
        ]],
        page: false,
        parseData: function (res) {
            console.log(">>>>>", res);
            if (res.errCode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.code,
                "data": res.data,
                "count": res.data.totalElements,
                "limit": 18
            }
        }
    });

    table.on("tool(cards)", function (obj) {
        var data = obj.data;
        switch (obj.event) {

            case "edit":
                $.ajax({
                    url: "/admin/NinePlan/findAll" ,
                    method: "GET",
                    success: function (res) {
                        if (!res || res.errCode != 0) {
                            layer.msg(res ? res.errMsg : "请求出错");
                            return;
                        }

                        var info = res.data;
                         console.log(info)
                        vm.id=info[0].id;
                        vm.ninePlan=info[0].ninePlan;
                        vm.ninePicture=info[0].ninePicture;
                        vm.logo_result="上传成功";
                        layer.open({
                            title: `详情`,
                            type: 1,
                            content: $(".add-subcat"),
                            area: ["650px", "500px"],
                        });

                        form.render("select");
                    }
                })
                break;
        }
    });

    $(".modal").click(function () {
        vm.id = "";
        layer.open({
            title: "添加打卡点",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        });

        form.render("select");
    })
});