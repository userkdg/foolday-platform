var vm = new Vue({
    el: '#nowOrder',
    data: {
        orderList: [],
        loadMore: 1,
        page: 1
    },
    methods: {
        goBack() {
            location.replace("http://community.jystu.cn/activity/web/wpIndex?random=" + Math.random());

        },
        // 将时间戳转换为 08：16 的形式
        format(stamp) {
            var date = new Date(stamp);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hours = date.getHours();
            var minutes = date.getMinutes();
            hours = hours < 10 ? '0' + hours : hours;
            minutes = minutes < 10 ? '0' + minutes : minutes;
            return `${year}/${month}/${day} ${hours}:${minutes}`
        },
        // 获取预约列表
        getTimerList(page) {
            win.showLoading();
            var _self = this;
            $.ajax({
                url: "/activity/web/resverseList",
                method: "GET",
                data: {
                    page: page
                },
                success: function (res) {
                    if(!res || res.errcode != 0) {
                        win.hideLoading();
                        return;
                    }
                    var timer = res.data.reserves;
                    if(timer.length == 0) {
                        _self.loadMore = 0;
                        win.hideLoading();
                        return;
                    }
                    timer.forEach((val, index)=> {
                        timer[index].createDate = _self.format(timer[index].createDate)
                    });
                    _self.orderList = _self.orderList.concat(timer);
                    _self.page += 1;
                    win.hideLoading();
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("x-access-token", localStorage.getItem('userToken'));
                }
            });
        },
        // 取消预约
        cancelReserve(e) {
            win.showLoading();
            var _self = this;
            $.ajax({
                url: "/activity/web/cancelReserve",
                method: "GET",
                data: {
                    id: e.target.dataset.id
                },
                success: function (res) {
                    win.hideLoading();
                    if(!res || res.errcode != 0) {
                        win.showMessage("取消失败", res?res.description:"");
                        return;
                    }
                    // 删除页面中的对应记录
                    _self.orderList[e.target.dataset.index].state = 3;
                    win.hideLoading();
                    win.showMessage("取消成功");
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("x-access-token", localStorage.getItem('userToken'));
                }
            });
        },
        // 加载更多
        toLoadMore() {
            this.getTimerList(this.page);
        }
    },
    beforeMount() {
        this.getTimerList(this.page);
    }
})