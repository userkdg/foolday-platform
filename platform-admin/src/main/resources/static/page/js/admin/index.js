var vm = new Vue({
    el: "#container",
    data: {
    },
    methods: {
        init: function () {
            var _that = this;
            $.ajax({
                url: "/activity/admin/medical/calcuMedData",
                success: function (res) {
                    if (!res || res.errcode != 0) {
                        layer.msg(res ? res.description : "请求出错！");
                        return;
                    }
                    var dat = res.data;
                    var myChart = echarts.init(document.getElementById("platformIncome"));
                    var opt = opts.newIncomeOpts([
                        dat.calcu_users,
                        dat.calcu_records
                    ]);
                    myChart.setOption(opt);

                    $(".month").attr("data-month", now_month)
                }
            })
        }
    }
});

var now_month = new Date().getMonth() + 1;
window.onload = function () {
    vm.init();
}
$("#last-month").click(function (e) {
    var month = $("#last-month").attr("data-month") || now_month;
    var date = calcu_month(month, 1);
    var url = "/activity/admin/medical/calcuMedData?year=" + date.year + "&month=" + date.month;
    $.get(url, function (res) {
        Income(res.data.calcu_users, res.data.calcu_records, date.month);
    })
});
$("#next-month").click(function (e) {
    var month = $("#last-month").attr("data-month") || now_month;
    var date = calcu_month(month, 2);
    var url = "/activity/admin/medical/calcuMedData?year=" + date.year + "&month=" + date.month;
    $.get(url, function (res) {
        Income(res.data.calcu_users, res.data.calcu_records, date.month);
    })
});

function Income(income, exp, nowMonth) {
    var myChart = echarts.init(document.getElementById("platformIncome"));
    var opt = opts.newIncomeOpts([income, exp]);
    myChart.setOption(opt);

    $(".month").attr("data-month", nowMonth)
}

function calcu_month(month, type) {
    month = parseInt(month);
    var now_year = new Date().getFullYear();
    if (type == 1) { //点击上个月
        month -= 1;
        if (month == 0) {
            month = 12;
            now_year -= 1;
        }
    } else if (type == 2) {   //点击下个月
        month += 1;
        if (month == 13) {
            month = 1;
            now_year += 1;
        }
    }
    return {
        year: now_year,
        month
    }
}