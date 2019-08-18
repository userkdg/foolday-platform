var vm = new Vue({
    el: "#container",
    data: {
        actr: {}
    },
    methods: {
        initActr: function () {
            vm.actr = {};
        }
    }
});


layui.use(['table', 'layer', "jquery", "laydate", "util", "form", "upload"], function () {
    var table = layui.table;
    var form = layui.form;
    var util = layui.util;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var upload = layui.upload;

    laydate.render({
        elem: '#activityTime'
        , type: 'datetime'
    });

    form.on("submit(add_activity)", function (obj) {
        var data = obj.field;
        $.ajax({
            url: "/admin/activityReservation/save",
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

    form.on("submit(update_actr)", function (obj) {
        var data = obj.field;
        data.activityName = $('#activityName').val();
        data.memberName = $('#memberName').val();
        data.contacts = $('#contacts').val();
        data.phone = $('#phone').val();
        data.email = $('#email').val();
        data.persionNumber = $('#persionNumber').val();
        data.activityTime = $('#activityTime').val();
        data.hotelArea = $('#hotelArea').val();
        data.hotel = $('#hotel').val();
        data.remark = $('#remark').val();
        $.ajax({
            url: "/admin/activityReservation/edit?id=" + vm.act.id,
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
        url: '/admin/activityReservation/findAll',
        title: '活动预订列表',
        method: 'get',
        page: true,
        size: 10,
        size: [5, 15, 25],
        cols: [
            [
                {type: 'numbers', width: 80, align: "center", title: '序号'},
                {field: 'activityName', title: '活动名称', align: "center"},
                {field: 'contacts', title: '联系人', align: "center"},
                {field: 'phone', title: '联系电话', align: "center"},
                {field: 'email', title: '邮箱', align: "center"},
                {field: 'persionNumber', title: '活动人数', align: "center"},
                {field: 'remark', title: '备注', align: "center"},
                {field: 'reservationNumber', title: '预订单号', align: "center"},
                {field: 'hotel', title: '活动酒店', align: "center"},
                {field: 'hotelArea', title: '活动地区', align: "center"},
                {field: 'activityTime', title: '活动时间', align: "center"},
                {field: 'submitTime', title: '提交时间', align: "center"},
                {field: 'status', title: '状态', align: "center"},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item', width: '20%'}
            ]
        ],
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错");
                return {};
            }

            res.data = res.data.map(function (item) {
                item.activityTime = util.toDateString(item.activityTime, "yyyy-MM-dd HH:mm:ss");
                item.submitTime = util.toDateString(item.submitTime, "yyyy-MM-dd ");
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
        switch (obj.event) {
            case 'edit':
                vm.initActr();
                vm.actr = obj.data;
                console.log(vm.actr);
                $('#activityName').val(vm.actr.activityName);
                $('#memberName').val(vm.actr.memberName);
                $('#contacts').val(vm.actr.contacts);
                $('#phone').val(vm.actr.phone);
                $('#email').val(vm.actr.email);
                $('#persionNumber').val(vm.actr.persionNumber);
                $('#activityTime').val(vm.actr.activityTime);
                $('#hotelArea').val(vm.actr.hotelArea);
                $('#hotel').val(vm.actr.hotel);
                $('#remark').val(vm.actr.remark);
                layer.open({
                    title: "添加活动",
                    content: $(".add_activity"),
                    type: 1,
                    area: ["700px", "550px"]
                });
                setTimeout(function () {
                    layui.form.render("select");
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
        vm.initActr();
        layer.open({
            title: "添加预约",
            content: $(".add_activity"),
            type: 1,
            area: ["700px", "550px"]
        });

        form.render("select");
    })

});


