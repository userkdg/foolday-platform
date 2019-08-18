layui.use(["table", "jquery", "layer", "laydate", "form"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var form = layui.form;
    var resState = ["已预约", "已执行", "已完成", "已取消"];
    var teState = ["科目一", "科目二", "科目三", "科目四"];

    var vm = new Vue({
        el: "#container",
        data: {
            start_time: "",
            end_time: "",
            total: 0
        },
        methods: {
            checkState: function() {
                $.ajax({
                    url: "/activity/admin/checkOPstate",
                    success: function(res) {
                        if(!res || res.errcode != 0) {
                            layer.msg(res ? res.description : "请求错误!!!");
                            return;
                        }
                        if(res.data.state == 1) {
                            layer.close(loading);
                            window.location.href = "http://community.jystu.cn/activity/admin/downloadFile?type=1";
                            return;
                        }
                        setTimeout(function() {
                            vm.checkState();
                        }, 1500);
                    }
                })
            },
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
        done: function(value, date, endDate){
            vm.start_time = value;
          }
    });

    laydate.render({
        elem: "#end_time",
        done: function(value, date, endDate) {
            vm.end_time = value;
        }
    });

    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("product_list", {
            page: {
                curr: 1
            },
            where: {
                name: sel_val
            }
        })

        return false;
    });

    table.render({
        elem: "#record_list",
        url: "/activity/admin/recordList",
        cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        limit: 15,
        limits: [15],
        id: "timer_list",
        cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'realname', align: "center", title: '真实姓名' },
            { field: 'username', align: "center", title: '联系方式' },
            { field: 'reverseTime', align: "center", title: '预约时间' },
            { field: 'coachId', align: "center", title: '所属教练' },
            { field: 'address', align: "center", title: '接送地点' },
            { field: 'grade', align: "center", title: '考试状态' },
            { field: 'state', align: "center", title: '预约状态' },
            { field: 'createDate', align: "center", title: '申请时间' },
            { title: '操作', fixed: "right", align: "center", toolbar: "#operator_item" }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错!!");
                return;
            }
            var reccords = res.data.records.map(record => {
                record.reverseTime = `${record.reserveDate} ${record.trainId.start_time}~${record.trainId.end_time}`;
                record.state = resState[+record.state];
                record.grade = teState[+record.degree];
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

    form.on("switch(used)", function(obj) {
        var checked = obj.elem.checked;
        var id = obj.value;
        checked = +checked;
        $.ajax({
            url: "/activity/admin/timerState",
            data: {
                id,
                checked
            },
            success: function(res) {
                if(!res || res.errcode != 0) {
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

    var loading;
    $(".output").click(function() {
        console.log(vm.start_time, vm.end_time);
        if(!vm.start_time || !vm.end_time) {
            layer.msg("请选择时间范围!");
            return;
        }
        loading = layer.load();
        $.ajax({
            url: "/activity/admin/recordOutput",
            data: {
                start_time: vm.start_time,
                end_time: vm.end_time
            },
            success: function(res) {
                if(!res || res.errcode != 0) {
                    layer.close(loading);
                    layer.msg(res ? res.description : "请求出错!");
                    return;
                }
                vm.checkState();
            },
            fail: function(err) {
                layer.close(loading);
                layer.msg("导出失败!!!");
                console.log(">>>err", err);
            }
        })
    })

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
                case "":
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