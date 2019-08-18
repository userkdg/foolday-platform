layui.use(["table", "jquery", "layer"], function () {
    var $ = layui.jquery;
    var table = layui.table;

    var vm = new Vue({
        el: "#container",
        data: {
            realname: "",
            courise: "",
            grade: "",
            registTime: "",
            mobile: ""
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

    table.render({
        elem: "#coach_list",
        url: "/activity/admin/teacherList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "coach_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'name', align: "center", title: '姓名' },
            { field: 'mobile', align: "center", title: '联系方式' },
            { field: 'teachNo', align: "center", title: '工号' },
            { field: 'courises', align: "center", title: '所授科目' },
            { field: 'grade', align: "center", title: '所授年级' },
            { field: 'inTime', align: "center", title: '入职时间' },
            { field: 'createDate', align: "center", title: '创建时间' },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" },
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            var teachers = res.data.teachers.map(tea => {
                tea.createDate = vm.formatDate(tea.createDate, "YYYY/MM/DD HH:mm");
                tea.courises = tea.courises.join(",");
                return tea;
            })
            return {
                "code": res.errcode,
                "data": teachers,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    $(".modal").click(function () {
        vm.name = "";
        vm.mobile = "";

        layer.open({
            title: "添加教师",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "350px"]
        });
    })

    table.on("tool(coachs)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/teacherDel",
                            data: {
                                id: data._id
                            },
                            success: function(res) {
                                if(!res || res.errcode != 0) {
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

    $("#teacher_info").submit(function (e) {
        console.log(">>>>>>", vm.realname);
        $.ajax({
            url: "/activity/admin/teacherAdd",
            method: "POST",
            data: {
                realname: vm.realname,
                mobile: vm.mobile,
                registTime: vm.registTime,
                grade: vm.grade,
                courise: vm.courise
            },
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