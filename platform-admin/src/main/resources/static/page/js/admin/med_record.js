layui.use(["table", "jquery", "layer", "laydate", "form"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;

    var vm = new Vue({
        el: "#container",
        data: {
            start_time: "",
            end_time: "",
            mobile: "",
            questions: [],
            results: {}
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

    laydate.render({
        elem: "#start_time",
        done: function (value, date, endDate) {
            vm.start_time = value;
        }
    });

    laydate.render({
        elem: "#end_time",
        done: function (value, date, endDate) {
            vm.end_time = value;
        }
    });


    $("#select_record").submit(function (e) {
        table.reload("timer_list", {
            page: {
                curr: 1
            },
            where: {
                mobile: vm.mobile,
                start_time: vm.start_time,
                end_time: vm.end_time
            }
        })

        return false;
    });

    table.render({
        elem: "#record_list",
        url: "/activity/admin/medical/medRecords",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "timer_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'username', align: "center", title: '联系方式' },
            { field: 'sex', align: "center", title: '性别' },
            { field: 'age', align: "center", title: '年龄' },
            { field: 'content', align: "center", title: '诊断结果' },
            { field: 'createDate', align: "center", title: '诊断时间' },
            { title: '操作', align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            var reccords = res.data.records.map(record => {
                record.username = record.uid ? record.uid.username : "17875334974";
                record.createDate = vm.formatDate(record.createDate, "YYYY-MM-DD HH:mm");
                return record;
            })
            return {
                "code": res.errcode,
                "data": reccords,
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
            url: "/activity/admin/timerState",
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
        vm.total = 0;
        vm.start_time = "";
        vm.end_time = "";

        layer.open({
            title: "添加预约记录",
            content: $(".add-subcat"),
            type: 1,
            area: ["700px", "350px"]
        });
    });

    table.on("tool(records)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "del":
                layer.open({
                    title: "系统提示",
                    content: "确定要删除吗？",
                    yes: function () {
                        $.ajax({
                            url: "/activity/admin/timerDel",
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
            case "result":
                $.ajax({
                    url: "/activity/admin/medical/quesData",
                    data: {
                        rid: data._id.toString()
                    },
                    success: function (res) {
                        if (!res || res.errcode != 0) {
                            layer.msg(res ? res.description : "请求出错!!!");
                            return false;
                        }

                        var questions = res.data.questions.map(question => {
                            if(question.value.question == "请选择您的症状？") {
                                console.log(">>>>>answer", question.value);
                                var answer = JSON.parse(question.value.answer);
                                question.value.answer = answer[2].value;
                            }
                            return question;
                        })
                        
                        vm.questions = questions;
                        
                        layer.open({
                            title: `${data.username}的诊断过程`,
                            content: $(".detail-subcat"),
                            type: 1,
                            area: ["700px", "600px"]
                        });
                    }
                })
                break;
            case "detail":
                $.ajax({
                    url: "/activity/admin/medical/resultData",
                    data: {
                        rid: data._id.toString()
                    },
                    success: function (res) {
                        if (!res || res.errcode != 0) {
                            layer.msg(res ? res.description : "请求出错!!!");
                            return false;
                        }
                        vm.results = res.data.results;

                        layer.open({
                            title: `${data.username}的诊断结论及治疗方式`,
                            content: $(".result-subcat"),
                            type: 1,
                            area: ["700px", "600px"]
                        });
                    }
                })
                break;
        }
    });

    $("#goods_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};
        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        $.ajax({
            url: "",
            data,
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