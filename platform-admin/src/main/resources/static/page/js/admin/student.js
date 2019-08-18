var vm = new Vue({
    el: "#container",
    data: {
        coachs: []
    },
    methods: {
        formatDate: function (datetime, pattern) {
            var date = new Date();
            date.setTime(datetime);
            var year = date.getFullYear();
            var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
            var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            var hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
            var minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
            var second = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
            return pattern.replace(/YYYY/igm, year)
                .replace(/MM/gm, month)
                .replace(/DD/igm, day)
                .replace(/HH/igm, hour)
                .replace(/mm/gm, minute)
                .replace(/ss/gm, second);
        }
    }
});

layui.use(["table", "jquery", "layer", "laydate", "form"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var form = layui.form;

    $.ajax({
        url: "/activity/admin/coachList",
        data: {
            page: 1
        },
        success: function (res) {
            if (!res || res.errcode != 0) return;
            vm.coachs = res.data.coach_list;
        }
    })

    laydate.render({
        elem: '#registTime'
    })

    table.render({
        elem: "#stu_list",
        url: "/activity/admin/studentList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "stu_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'realname', align: "center", title: '真实姓名' },
            { field: 'username', align: "center", title: '账号' },
            { field: 'registTime', align: "center", title: '报名时间' },
            { field: 'degree', align: "center", title: '考试状态' },
            { field: 'address', align: "center", title: '接送地点' },
            { field: 'coachId', align: "center", title: '所属教练' },
            { field: 'createDate', align: "center", title: '添加时间' },
            { toolbar: "#use_status", align: "center", title: '当前状态' },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" },
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            var stu_list = res.data.student_list.map(stu => {
                stu.coachId = stu.coachId.name;
                stu.createDate = vm.formatDate(stu.createDate, "YYYY-MM-DD HH:mm:ss");
                return stu;
            })
            return {
                "code": res.errcode,
                "data": stu_list,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        checked = +checked;
        $.ajax({
            url: "/activity/admin/stuState",
            data: {
                id,
                checked
            },
            success: function (res) {
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "请求有误!!!");
                    return;
                }
                layer.msg("操作成功");
            }
        })
    })

    $(".modal").click(function () {
        form.render('select');
        layer.open({
            title: "添加学员",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "550px"]
        });
    });

    $(".group_add").click(function() {
        var idx = layer.open({
            title: "批量导入学员信息",
            content: "/activity/admin/view/stuInputView",
            type: 2,
            area: ["700px", "350px"],
            end: function() {
                location.reload();
            }
        });
    })

    table.on("tool(stus)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/stuDel",
                            data: {
                                id: data._id
                            },
                            success: function (res) {
                                if (!res || res.errcode != 0) {
                                    layer.msg(res ? res.description : "请求失败!!!");
                                    return;
                                }
                                layer.msg("操作成功!!!");
                                location.reload();
                            }
                        });
                        return true;
                    }
                });
                break;
        }
    });

    $("#student_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        $.ajax({
            url: "/activity/admin/stuAdd",
            data,
            method: "POST",
            success: function (res) {
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "请求出错!!!");
                    return false;
                }
                location.reload();
            }
        });
        return false;
    });
});