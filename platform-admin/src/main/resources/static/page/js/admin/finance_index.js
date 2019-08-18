
var vm = new Vue({
    el: ".right",
    data: {
        financeData: {
            count: 0,
            volum: 0, 
            first_child: 0,
            second_child: 0,  
            prize: 0,
            bonus: 0,
            prepay: 0,
            withdrawal: 0,
            rest_money: 0,
            money: 0
        }
    },
    methods: {
        requestDaTa: function (page) {
            $.ajax({
                url: "http://pro.yaliankeji.cn/?action=admin.finance.finance_system",
                data: {

                },
                success: function (res) {
                    if (res) {
                        if (res.errcode == 0) {
                            if (res.system) {
                                for (key in vm.financeData) {
                                    vm.financeData[key] = +(res.system[key]).toFixed(2);
                                }
                            }
                        } else {
                            alert(res.errmsg);
                            return;
                        }
                    }
                },
                error: function () {
                    alert("网络异常,请刷新后重试");
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
})

vm.requestDaTa(1);

layui.use(['table', 'layer', "jquery", "upload", "layedit"], function () {
    var table = layui.table;
    var layer = layui.layer;
    var form = layui.form;
    var layedit = layui.layedit;


    $("#select_goods").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        table.reload("finance_list", {
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
        elem: '#finance_list'
        , url: '/?action=admin.system.finance_daily_list'
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "finance_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'date', align: "center", title: '日报日期' },
            { field: 'count', align: "center", title: '总台数' },
            { field: 'volum', align: "center", title: '营业总额' },
            { field: 'shareMoney', align: "center", title: '分享总金' },
            { field: 'thankMoney', align: "center", title: '感恩总金' },
            { field: 'prize', align: "center", title: '奖励总金' },
            { field: 'bonus', align: "center", title: '分红总金' },
            { field: 'prepay', align: "center", title: '已提现金额' },
            { field: 'withdrawal', align: "center", title: '待提现金额' }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res.descriptions || "请求出错!!!");
                return {};
            }
            var dailyList = res.daily_list || ''
            dailyList = dailyList.map(daily_info => {
                daily_info.createDate = vm.formatDate(daily_info.createDate, 'YYYY-MM-DD HH:mm:ss');
                return daily_info;
            })
            return {
                "code": res.errcode,
                "data": dailyList,
                "count": res.total,
                "limit": 15
            }
        }
    });
});