layui.use(["table", "jquery", "layer", "upload"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var upload = layui.upload;

    var vm = new Vue({
        el: "#container",
        data: {
            uid: "",
            couriseId: "",
            grade: "",
            year: "",
            filename: "",
            filepath: ""
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

    upload.render({
        elem: '#choose_file',
        url: "/activity/admin/uploadFile",
        accept: "file",
        exts: 'xls|xlsx',
        done: function(res) {
            if(!res || res.errcode != 0) {
                layer.msg(res ? res.description : "上传失败");
                return;
            }
            vm.filepath = res.data.filepath;
            vm.filename = res.data.filename;
            layer.msg("上传成功");
        }
    });

    table.render({
        elem: "#achieve_list",
        url: "/activity/admin/achieveList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "achieve_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'name', align: "center", title: '学生姓名' },
            { field: 'classroom', align: "center", title: '所属学院' },
            { field: 'courseName', align: "center", title: '所属课程' },
            { field: 'grade', align: "center", title: '成绩' },
            { field: 'year', align: "center", title: '所属学年' },
            { field: 'createDate', align: "center", title: '创建时间' },
            { title: '操作', toolbar: "#operator_item" },
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            var records = res.data.records.map(rec => {
                rec.createDate = vm.formatDate(rec.createDate, "YYYY/MM/DD HH:mm");
                rec.name = rec.uid.username;
                rec.classroom = rec.uid.classroom;
                rec.courseName = rec.couriseId.name;
                return rec;
            })
            return {
                "code": res.errcode,
                "data": records,
                "count": res.data.total,
                "limit": 15
            }
        }
    });

    $(".modal").click(function () {
        vm.name = "";
        vm.mobile = "";

        layer.open({
            title: "添加学生成绩",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "350px"]
        });
    });

    table.on("tool(achieves)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/achiDel",
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

    $("#achieve_info").submit(function (e) {
        $.ajax({
            url: "/activity/admin/achInput",
            method: "POST",
            data: {
                filepath: vm.filepath
            },
            success: function (res) {
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "上传失败");
                    return false;
                }
                layer.msg("导入成功!!!");
                setTimeout(function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }, 500);
            }
        });
        return false;
    });
});