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

    table.render({
        elem: '#bonus_list'
        , url: '/?action=admin.finance.finance_list'
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 15,
        limits: [15],
        id: "bonus_list"
        , cols: [[
            { type: "numbers", width: 80, align: "center", title: '序号' },
            { field: 'newName', width: 180, align: "center", title: '用户姓名' },
            { field: 'first_child', width: 180, align: "center", title: '分享总金' },
            { field: 'second_child', width: 180, align: "center", title: '奖励总金' },
            { field: 'bonus', width: 180, align: "center", title: '分红金' },
            { field: 'withdrawal', align: "center", align: "center", title: '已提现金额' },
            { field: 'prepay', width: 180, align: "center", title: '待打款金额' }
        ]],
        page: true,
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res.descriptions || "请求出错!!!");
                return {};
            } 
            var financeList = res.finance_list
            financeList = financeList.map(financeInfo => {
                financeInfo.newName = financeInfo.uid.username || financeInfo.uid.nickname;
                return financeInfo
            })
           
            return {
                "code": res.errcode,
                "data": res.finance_list,
                "count": res.total,
                "limit": 15
            }
        }
    });
    
});