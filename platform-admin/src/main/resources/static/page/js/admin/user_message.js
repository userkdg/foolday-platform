
var action = "add";

var vm = new Vue({
    el: "#container",
    data: {
        grades: [{ name: "请选择发放等级", grade: -1 }],
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
})
layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;

    $("#message_info").submit(function (e) {
        var params = $(this).serializeArray();
        var data = {};

        for (var i = 0; i < params.length; i++) {
            var dat = params[i];
            data[dat.name] = dat.value;
        }
        if (action == "add") {
            action = "admin.system.msg_add";
        } else if (action == "edit") {
            action = "admin.system.msg_edit";
        }
        data.action = action;
        $.ajax({
            url: "/",
            method: "POST",
            data,
            success: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.description);
                    return;
                }
                location.reload();
            }
        });
        return false;
    });

    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("message_list", {
            page: {
                curr: 1
            },
            where: {
                name: sel_val
            }
        })

        return false;
    })

    table.render({
        elem: '#message_list'
        , url: '/?action=admin.user.message_list'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "message_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'title', width: 180, title: '喜讯标题' },
            { field: 'content', title: '喜讯内容' },
            { field: 'createDate', title: '创建时间'},
        ]],
        page: true,
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            let messageList = res.message_list;
            messageList = messageList.map(message_info => {
                message_info.createDate = vm.formatDate(message_info.createDate, 'YYYY-MM-DD HH:mm:ss')
                return message_info;
            })
            return {
                "code": res.errcode,
                "data": res.message_list,
                "count": res.total,
                "limit": 15
            }
        }
    });

});