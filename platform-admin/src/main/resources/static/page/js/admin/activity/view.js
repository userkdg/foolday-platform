var vm = new Vue({
    el: "#container",
    data: {
        hotals: [],
        hotalId: "",
        img: "",
        upload_result: "上传中...",
        chooseHotals: [],
        name: "",
        act: {}
    },
    methods: {
        initAct: function () {
            vm.act = {};
            vm.img = "";
            vm.chooseHotals = [];
            vm.upload_result = "上传中...";
        },
        clear: function () {
            this.img = "";
            this.upload_result = "上传中...";
        },
        delSpec: function (index) {
            vm.chooseHotals.splice(index, 1)
        }
    }
})

layui.use(['table', 'layer', "jquery", "util", "form", "upload"], function () {
    var table = layui.table;
    var form = layui.form;
    var util = layui.util;
    var $ = layui.jquery;
    var upload = layui.upload;

    var ue = UE.getEditor('activityDetails');

    function setContent(text) {
        UE.getEditor('activityDetails').ready(function () {
            UE.getEditor('activityDetails').execCommand('insertHtml', text)
        });
    }

    function getContent() {
        return UE.getEditor('activityDetails').getContent();
    }


    $.ajax(({
        url: "/admin/hotel/getHotelName",
        method: "GET",
        dataType: "json",
        success: function (res) {
            console.log(">>>>>>", res);
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错");
                return;
            }

            vm.hotals = res.data;
        }
    }));

    form.on("select(hotalId)", function (obj) {
        var index = obj.value;
        for (var idx in vm.chooseHotals) {
            if (vm.chooseHotals[idx].id == vm.hotals[index].id) {
                layer.msg("不可重复添加！");
                return;
            }
        }
        vm.chooseHotals.push(vm.hotals[index]);
    })

    upload.render({
        elem: "#choose_img",
        url: "/file/upload",
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.img = result;
            });
        },
        done: function (res) {
            console.log(">>>>>>res", res);
            if (res && res.errcode == 0) {
                vm.upload_result = "上传成功!";
                vm.img = res.data;
            } else {
                vm.upload_result = "上传失败!!!";
            }
        }
    });

    form.on("submit(add_activity)", function (obj) {
        var data = obj.field;
        var ids = vm.chooseHotals.map(function (hotal) {
            return hotal.id;
        }).join(",");
        data.hotelId = ids;
        data.coverPhoto = vm.img;
        data.activityDetails = getContent();

        $.ajax({
            url: "/admin/activityReleased/save",
            method: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                console.log(res);
                res = JSON.parse(res);
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.errmsg : "请求出错");
                    return false;
                }

                location.reload();

            }
        });

        return false;
    })

    form.on("submit(update_act)", function (obj) {
        var data = obj.field;
        var ids = vm.chooseHotals.map(function (hotal) {
            return hotal.id;
        }).join(",");
        data.hotelId= ids;
        data.coverPhoto = vm.img;
        data.activityDetails=getContent();
        data.activityName = $('#activityName').val();
        $.ajax({
            url: "/admin/activityReleased/edit?id="+vm.act.id,
            method: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                console.log(res);
                res = JSON.parse(res);
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.errmsg : "请求出错");
                    return false;
                }

                location.reload();

            }
        });

        return false;
    })


    table.render({
        elem: '#dataView',
        url: '/admin/activityReleased/view',
        title: '活动列表',
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 15,
        limits: [15],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {toolbar: '#img_detail', title: '封面图片', align: "center"},
                {field: 'activityName', title: '活动名称', align: "center"},
                {field: 'city', title: '活动地区', align: "center"},
                {field: 'province', title: '活动城市', align: "center"},
                {field: 'hotelName', title: '活动酒店', align: "center"},
                {field: 'activityIntroduction', title: '活动简介', align: "center"},
                {field: 'releaseTime', title: '发布时间', align: "center"},
                {field: 'activityDetails', title: '活动详情', align: "center"},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: "20%"}
            ]
        ],
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错");
                return {};
            }

            res.data = res.data.map(function (item) {
                item.releaseTime = util.toDateString(item.releaseTime, "yyyy-MM-dd HH:mm:ss");
                return item;
            });

            return {
                "code": res.errcode,
                "data": res.data,
                "count": res.count,
                "size": 15
            }
        }
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        console.log(vm.act);
        switch (obj.event) {
            case 'edit':
                vm.initAct();
                vm.act=obj.data;
                console.log(vm.act);
                var hotelstr = vm.act.hotelName.substr(1)
                hotelstr = hotelstr.substr(0, hotelstr.length - 1);
                vm.chooseHotals=hotelstr.split(",")
                    .map(o=>{
                        console.log("-----o----", o);
                        return vm.hotals.find(h => h.name === $.trim(o))
                    })
                    .filter(o => o);
                $("#activityName").val(vm.act.activityName);
                vm.img = vm.act.coverPhoto;
                vm.upload_result="";
                layer.open({
                    title: "添加活动",
                    content: $(".add-activity"),
                    type: 1,
                    area: ["700px", "550px"]
                });
                setTimeout(function () {
                    layui.form.render("select");
                    setContent(vm.act.activityDetails);
                    form.render('select');
                    form.render("checkbox")
                }, 500);
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/activityReleased/delReleased?id=" + data.id,
                        data: {
                            ids: data.id
                        },
                        type: "post",
                        dataType: "json",
                        success: function (result) {
                            if (!result || result.errcode != 0) {
                                layer.msg(res ? res.errmsg : '网络异常,请稍后再试');
                                return;
                            }
                            location.reload();
                        }
                    });
                });
                break;
        }
        ;
    });
    $(".modal").click(function () {
        vm.initAct();
        layer.open({
            title: "添加活动",
            content: $(".add-activity"),
            type: 1,
            area: ["700px", "550px"]
        });
        form.render("select");
    })
});
