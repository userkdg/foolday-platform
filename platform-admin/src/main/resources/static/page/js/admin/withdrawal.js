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

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;


    $(".modal").click(function () {
        $("#withDraw_info").get(0).reset();
        action = "add";
        layer.open({
            title: "是否提现",
            type: 1,
            content: $(".add-subcat"),
            area: ["650px", "400px"],
        })
    })

    table.render({
        elem: '#bonus_list'
        , url: '/?action=admin.finance.withdrawal_list'
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "bonus_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'username', width: 180, align: "center", title: '提现人' },
            { field: 'bank', width: 180, align: "center", title: '开户银行' },
            { field: 'account', width: 180, align: "center", title: '银行卡账号' },
            { field: 'mobile', width: 180, align: "center", title: '联系方式' },
            { field: 'money', align: "center", align: "center", title: '提现金额' },
            { field: 'real_money', width: 180, align: "center", title: '手续费' },
            { title: '状态', fixed: "right", width: 180, align: "center", toolbar: "#state_item" },
            { title: '操作', fixed: "right", width: 180, align: "center", toolbar: "#operator_item" }

        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res.descriptions || "请求出错!!!");
                return {};
            }
            var withdrawals = res.withdrawals
            withdrawals = withdrawals.map(withDraw => {
                if (withDraw.state == 0) {
                    withDraw.state_content = '申请中'
                    withDraw.className = "layui-btn layui-btn-xs layui-btn layui-btn-warm";
                } else if (withDraw.state == 1) {
                    withDraw.state_content = '提现成功'
                    withDraw.className = "layui-btn layui-btn-xs layui-btn";
                } else {
                    withDraw.state_content = '提现失败'
                    withDraw.className = "layui-btn layui-btn-xs layui-btn layui-btn-danger";
                }
                withDraw.real_money = withDraw.real_money - withDraw.money;
                return withDraw;
            })
            return {
                "code": res.errcode,
                "data": withdrawals,
                "count": res.total,
                "limit": 15
            }
        }
    });

    var window_sure;
    table.on("tool(bonus)", function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case "change":
                window_sure = layer.open({
                    title: `${data.username}的提现`,
                    content: '已经提现至对应账户?'
                    , btn: ['是', '否']
                    , yes: function () {
                        //按钮【按钮一】的回调
                        getWithDraw(data._id, 1)
                        layer.close(window_sure);
                    }
                    , btn2: function () {
                        getWithDraw(data._id, 2)

                    }
                });
                break;
            case "detail":
                $.ajax({
                    url: "/?action=admin.finance.withdrawal_info",
                    method: "GET",
                    data: {
                        id: data._id
                    },
                    success: function (res) {
                        console.log(">>>>>>>>res", res)
                        if (res.errcode != 0) {
                            layer.msg(res.description);
                            return;
                        }
                        var info = res.info
                        if (info.state == 0) {
                            info.state_content = '申请中'
                        } else if (info.state == 1) {
                            info.state_content = '提现成功'
                        } else {
                            info.state_content = '提现失败'
                        }

                        $(".username").html(info.username);
                        $(".bank").html(info.bank);
                        $(".account").html(info.account);
                        $(".mobile").html(info.mobile);
                        $(".real_money").html(info.real_money);
                        $(".money").html(info.money);
                        $(".state_content").html(info.state_content);

                        layer.open({
                            title: "提现详情",
                            type: 1,
                            content: $(".detail-subcat"),
                            area: ['600px', '350px']
                        });
                    }
                })
                break;
        }
    });
    function getWithDraw(id, state) {
        $.ajax({
            url: "/?action=admin.finance.withdrawal_status",
            method: "GET",
            data: {
                id,
                state
            },
            success: function (res) {
                location.reload();
            }
        })
    }
});