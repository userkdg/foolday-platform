var vm = new Vue({
    el: '#inlineOrder',
    data: {
        hasAuth: false,
        identify: {},
        availableTime: [],
        selectTime: '',
        showBox: false,
        orderList: [],
        token: ''
    },
    methods: {
        goLogin() {
            location.replace("http://community.jystu.cn/activity/web/wpLogin");
        },
        goHistory() {
            location.replace("http://community.jystu.cn/activity/web/nowOrder");
        },
        // 获取未来某天的日期
        getFutureDate(future) {
            var currentDate = new Date();
            var date =new Date(currentDate.setDate(currentDate.getDate() + future))
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            month = month < 10? '0' + month : month;
            day = day < 10? '0' + day : day;
            return year + "-" + month + "-" + day;
        },
        // 获取用户基本信息
        getUserInfo() {
            var _self = this;
            $.ajax({
                url: "/activity/web/userinfo",
                method: "GET",
                success: function (res) {
                    if(!res || res.errcode != 0) {
                        return;
                    }
                    var userinfo = res.data.userinfo;
                    _self.hasAuth = true;
                    _self.identify.img = userinfo.headIcon || '/activity/img/webpage/user.png';
                    _self.identify.nickName = userinfo.nickName || '美国队长';
                    _self.identify.phone = userinfo.mobile;
                    _self.identify.state = userinfo.state;
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("x-access-token", _self.token);
                }
            });
        },
        // 获取预约列表
        getTimerList() {
            var _self = this;
            $.ajax({
                url: "/activity/web/timerList",
                method: "GET",
                data: {
                    date: _self.selectTime
                },
                success: function (res) {
                    if (res.errcode == 0) {
                        _self.orderList = res.data.timers;
                    }
                },
                beforeSend: function(xhr) {
                    _self.token ? xhr.setRequestHeader("x-access-token", _self.token) : '';
                }
            });
        },
        // 选择"可预约时间"
        toSelect(e) {
            var target = e.target;
            if(target.nodeName == "LI") {
                this.selectTime = this.availableTime[target.dataset.id];
                this.getTimerList();
            }
            this.showBox = false;
        },
        alterState(e) {
            // 显示加载框
            win.showLoading();
            // 如果点击的是取消按钮，则找取消预约方法
            if (this.orderList[e.target.dataset.index].isRes) {
                this.cancelReserve(e);
                return;
            }
            // 点击了立即预约
            var _self = this;
            $.ajax({
                url: "/activity/web/reserveOne",
                method: "GET",
                data: {
                    id: e.target.dataset.id,
                    date: _self.selectTime
                },
                success: function (res) {
                    win.hideLoading();
                    if(!res || res.errcode != 0) {
                        win.showMessage("预约失败", res?res.description:"");
                        return;
                    }
                    _self.orderList[e.target.dataset.index].isRes = true;
                    win.showMessage("预约成功");
                    _self.getTimerList();
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("x-access-token", _self.token);
                }
            });
        },
        // 取消预约
        cancelReserve(e) {
            var _self = this;
            $.ajax({
                url: "/activity/web/cancelIndex",
                method: "GET",
                data: {
                    id: e.target.dataset.id,
                    date: _self.selectTime
                },
                success: function (res) {
                    win.hideLoading();
                    if(!res || res.errcode != 0) {
                        win.showMessage("取消失败", res?res.description:"");
                        return;
                    }
                    _self.orderList[e.target.dataset.index].isRes = false;
                    win.showMessage("取消成功", '');
                    _self.getTimerList();
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("x-access-token", _self.token);
                }
            });
        }
    },
    beforeMount() {
        this.token = localStorage.getItem('userToken');
        if (this.token) {
            this.getUserInfo();
        }
        // 设置"可预约时间列表"即"2019-4-1"这个字段的弹出框 设置为未来七天
        for(var i = 0; i < 5; i++) {
            this.availableTime[i] = this.getFutureDate(i + 1);
        }
        this.selectTime = this.availableTime[0];
        this.getTimerList();
    }
})
