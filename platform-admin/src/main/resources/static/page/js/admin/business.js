var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
        imgs: [],
        realName: "",
        storeName: "",
        busAddr: "",
        mobile: "",
        detail: ""
    },
    methods: {

    }
})

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var upload = layui.upload;
    var layedit = layui.layedit;


    table.render({
        elem: '#goods_list'
        , url: '/?action=admin.shop.business_list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "goods_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'realName', align: "center", title: '店主姓名' },
            { field: 'storeName',  align: "center", title: '联系方式' },
            { field: 'mobile',align: "center", title: '店铺名称' },
            { field: 'busAddr', align: "center", title: '店铺地址' },
            { field: 'detail', align: "center", title: '店铺介绍' },
            { align: "center", title: "启用状态", toolbar: "#use_status" },
            { title: '操作', fixed: "right", width: 210, align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res.descriptions || "请求出错!!!");
                return {};
            }
            
            return {
                "code": res.errcode,
                "data": res.business_list,
                "count": res.total,
                "limit": 15
            }
        }
    });

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        $.ajax({
            url: "/?action=admin.shop.business_state",
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
                            url: "/?action=admin.shop.business_del",
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
            case "detail":
                $.ajax({
                    url: "/?action=admin.shop.business_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        
                        var info = res.info;
                        vm.imgs = info.imgs;
                        vm.mobile = info.mobile;
                        vm.storeName = info.storeName;
                        vm.realName = info.realName;
                        vm.busAddr = info.busAddr;
                        vm.detail = info.detail;

                        var layer_window = layer.open({
                            title: "商品详情",
                            type: 1,
                            content: $(".goods-detail")
                        });
                        layer.full(layer_window);

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