var vm = new Vue({
    el: "#container",
    data: {
    	openid: "",
    	userName: "",
    	pictureText: "",
        pictureEw:"",
        pictureSn: "",
        sel_name:""
        
    },
    methods: {
        formarDate: function() {
        	
        }
    }
});

layui.use(['table', 'layer', "jquery", "form"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    $("#message_info").submit(function (e) {
       var allData={
                         	"openid": vm.openid,
                         	"userName": vm.userName,
                         	"pictureText": vm.pictureText,
                             "pictureEw":vm.pictureEw,
                             "pictureSn": vm.pictureSn};
               $.ajax({
                   contentType: 'application/json;charset=UTF-8',
                   dataType:"json",
                   url: "/admin/NinePicture/save",
                   method: "POST",
                   data: JSON.stringify(allData),
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

    table.render({
        elem: '#route_list'
        , url: '/admin/NinePicture/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'userName', title: '用户名' },
            { field: 'pictureText', title: '发布内容' },
            { toolbar: '#image_item', title: '图片' },
            { field: 'pictureEw', title: '九图客经度' },
            { field: 'pictureSn', title: '九图客纬度' },
            { field: 'pictureClick', title: '点赞量' },
//            {toolbar:"#status",title:"是否启用"},
            { field: 'pictureTime', title: '发布时间' },
            { title: '操作', width:200, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            console.log(">>>>>", res);
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

    table.on("tool(feedbacks)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/NinePicture/delete/"+data.pictureId,
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
                    url: "/admin/NinePicture/findOne",
                    method: "GET",
                    data: {
                        id: data.pictureId
                    },
                   success: function (res) {
                       if (res.errCode != 0) {
                        layer.msg(res ? res.errMsg : "请求出错");
                        return;
                                         }
                        var info = res.data;


                    form.render("select");
                    layer.open({
                     title: `详情`,
                      type: 1,
                      area: ["650px", "500px"],
                                          });
                    }
                })
                break;
        }
    });

    $(".modal").click(function () {
        layer.open({
            title: "添加公告",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "500px"],
        });
    })
});