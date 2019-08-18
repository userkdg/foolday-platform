var vm = new Vue({
    el: "#courses",
    data: {
        name: "",
        grade: "",
        teaId: "",
        total: "",
        classTime: "",
        totalTime: "",
        credit: "",
        classroom: "",
        teachers: [],
        week: "",
        year: "",
        college: "",
        years: ["2015~2016学年", "2016~2017学年", "2017~2018学年", "2018~2019学年"]
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
layui.use(["table", "jquery", "layer", "form"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;

    $.ajax({
        url: "/activity/admin/teacherList",
        data: {
            page: 1
        },
        success: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "参数有误!");
                return;
            }
            vm.teachers = res.data.teachers;
        }
    });

    form.on("switch(used)", function (obj) {
        var checked = obj.elem.checked;
        var id = obj.value;

        checked = checked ? 1 : 0;
        $.ajax({
            url: "/activity/admin/couriseState",
            method: "GET",
            data: {
                checked,
                id
            },
            success: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.description);
                    return;
                }
                layer.msg("操作成功!");
            }
        })
    })

    table.render({
        elem: "#course_list",
        url: "/activity/admin/couriseList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "course_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'name', align: "center", title: "课程名称" },
            { field: 'grade', align: "center", title: '所属年级' },
            { field: 'teachId', align: "center", title: '科任老师' },
            { field: 'count', align: "center", title: '学生总数' },
            { field: 'classTime', align: "center", title: '上课时间' },
            { field: 'courseTime', align: "center", title: '课程学时' },
            { field: 'courseCredit', align: "center", title: '课程学分' },
            { field: 'classroom', align: "center", title: '所属学院' },
            { toolbar: '#status', align: "center", title: "启用状态" },
            { field: 'createDate', align: "center", title: '创建时间' },
            { title: '操作', width: 200, align: "center", toolbar: "#operator_item" },
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            res.data.courises = res.data.courises.map(cour => {
                cour.teachId = cour.teachId.name;
                cour.createDate = vm.formatDate(cour.createDate, "YYYY-MM-DD HH:mm:ss");
                return cour;
            })
            return {
                "code": res.errcode,
                "data": res.data.courises,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    $(".modal").click(function () {
        vm.name = "";
        vm.mobile = "";

        form.render("select");
        layer.open({
            title: "添加课程",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "650px"]
        });
    })

    table.on("tool(courses)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/couriseDel",
                            data: {
                                id: data._id
                            },
                            success: function (res) {
                                if (!res || res.errcode != 0) {
                                    layer.msg(res ? res.description : "请求失败!!!");
                                    return;
                                }
                                location.reload();
                            }
                        });
                        return true;
                    }
                });
                break;
            case "detail":
                $.ajax({
                    url: "/activity/admin/couriseDetail",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        if (!res || res.errcode != 0) {
                            layer.msg(res ? res.description : "请求失败!!!");
                            return;
                        }

                        var info = res.data.info;
                        vm.name = info.name;
                        vm.grade = info.grade;
                        vm.teaId = info.teachId;
                        vm.total = info.total;
                        vm.classTime = info.classTime;
                        vm.totalTime = info.totalTime;
                        vm.credit = info.courseCredit;
                        vm.classroom = info.classroom;
                        vm.year = info.year;
                        vm.week = info.week;
                        vm.college = info.college;

                        layer.open({
                            title: `${info.name}的详情`,
                            content: $(".add-subcat"),
                            type: 1,
                            area: ["700px", "550px"]
                        });
                        form.render("select");
                    }
                })
                break;
        }
    });

    $("#courise_info").submit(function (e) {
        $.ajax({
            url: "/activity/admin/couriseAdd",
            method: "POST",
            data: {
                name: vm.name,
                grade: vm.grade,
                teachId: vm.teaId,
                count: vm.total,
                classTime: vm.classTime,
                courseTime: vm.totalTime,
                courseCredit: vm.credit,
                classroom: vm.classroom,
                year: vm.year,
                week: vm.week,
                college: vm.college
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