var vm = new Vue({
    el: "#container",
    data: {
        id: "",
        routeId: "",
        cardName: "",
        address: "",
        lat: "",
        lng: "",
        cardSort: "",
        routers: [],
         lat: "",
         lng: "",
         sel_name:""
    },
    methods: {
        formarDate: function () {

        },
     seachByLoc: function() {
          if(!this.address) {
              layer.msg("商家地址不能为空!");
              return;
          }
          codeAddress(this.address);
          return false;
      },
      loc_callback: function(result) {
          vm.lat = result.location.lat;
          vm.lng = result.location.lng;
      }
    }
});

layui.use(['table', 'layer', "jquery", "form"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;

    init();

    $.ajax({
        url: "/admin/Route/findAll",
        method: "GET",
        data: {
            page: 1,
            size: 10000
        },
        success: function (res) {
            if (!res || res.errCode != 0) {
                layer.msg(res ? res.errMsg : "请求出错!");
                return;
            }
            vm.routers = res.data.content;
        }
    })

    $("#message_info").submit(function (e) {
        $.ajax({
            contentType: 'application/json;charset=UTF-8',
            url: "/admin/Card/save",
            method: "POST",
            data: JSON.stringify({
                "cardId": vm.id,
                "routeId": vm.routeId,
                "cardEw": vm.lat,
                "cardSn": vm.lng,
                "cardName": vm.cardName,
                "address": vm.address,
                "cardSort": vm.cardSort,
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
        elem: '#card_list'
        , url: '/admin/Card/findAllContain'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'route_title', title: '路线名称' },
            { field: 'card_name', title: '打卡名称' },
            { field: 'card_ew', title: '打卡经度' },
            { field: 'card_sn', title: '打卡纬度' },
            { field: 'address', title: '所在地址' },
            { toolbar: '#status', title: '启用状态' },
            { field: 'card_sort', title: '排序字段' },
            { field: 'create_time', title: '创建时间' },
            { title: '操作', align: "center", toolbar: "#operator_item" }
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
    form.on("switch(status)", function (obj) {
        var checked = obj.elem.checked;
        var cardId = obj.value;
        var status = checked ? 1 : 0;
        console.log(status);
        $.ajax({
            url: "/admin/Card/onFire",
            method: "GET",
            data: {
                cardId,
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
    table.on("tool(cards)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/admin/Card/delCardId/" + data.card_id,
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
                    url: "/admin/Card/findByCardId/" + data.card_id,
                    method: "GET",
                    success: function (res) {
                        if (!res || res.errCode != 0) {
                            layer.msg(res ? res.errMsg : "请求出错");
                            return;
                        }
                        var info = res.data;
                        vm.id = info.cardId;
                        vm.routeId = info.routeId;
                        vm.lat = info.cardEw;
                        vm.lng = info.cardSn;
                        vm.cardName = info.cardName;
                        vm.address = info.address;
                        vm.cardSort = info.cardSort;
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