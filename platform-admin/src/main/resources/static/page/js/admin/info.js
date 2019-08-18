
var vm = new Vue({
    el: ".right",
    data: {
        userData: {
            mobile: '',
            prize: 0,
            level_prize: 0,
            prize_timeout: 0,
            min_withdrawal: 0,
            limit_withdrawal: 0,
            withdrawal_fee: 0,
            instructions: '',
            aboutus: '',
            _id: '',
            partner_favour: 0
        },
        content: '',
        showModalStatusLab: false

    },
    methods: {
        requestDaTa: function (page) {
            $.ajax({
                url: "http://pro.yaliankeji.cn/?action=admin.system.info",
                success: function (res) {
                    if (!res || res.errcode != 0) {
                        alert(res.description);
                        return;
                    }
                    for (key in vm.userData) {
                        vm.userData[key] = res[key];
                    }
                },
                error: function () {
                    alert("网络异常,请刷新后重试");
                }
            })
        },
        powerDrawerLab() {
            vm.showModalStatusLab = true
        },
        offService() {
            vm.showModalStatusLab = false
        },
        // 立即修改
        editBulletin() {
            if (!vm.userData) {
                alert("请输入完整的信息！！！");
                return false;
            };
            $.ajax({
                url: 'http://pro.yaliankeji.cn/?action=admin.system.info_edit',
                data: {
                    mobile: vm.userData.mobile,
                    prize: vm.userData.prize,
                    level_prize: vm.userData.level_prize,
                    prize_timeout: vm.userData.prize_timeout,
                    min_withdrawal: vm.userData.min_withdrawal,
                    limit_withdrawal: vm.userData.limit_withdrawal,
                    withdrawal_fee: vm.userData.withdrawal_fee,
                    instructions: vm.userData.instructions,
                    aboutus: vm.userData.aboutus,
                    id: vm.userData._id,
                    partner_favour: vm.userData.partner_favour
                },
                success: function (res) {
                    if (res.errcode == 0) {
                        vm.showModalStatusLab = false;
                        window.location.reload(true);
                    } else {
                        alert(res.description);
                    }
                },
                error: function (err) {
                    alert("网络错误");
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