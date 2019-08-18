var vm = new Vue({
    el: '#login-page',
    data: {
        username: '',
        password: ''
    },
    methods: {
        verify() {
            win.showLoading();
            var _self = this;
            $.ajax({
                url: "/activity/web/login",
                method: "POST",
                data: {
                    username: _self.username,
                    password: _self.password
                },
                success: function (res) {
                    win.hideLoading();
                    if(!res || res.errcode != 0) {
                        win.showMessage("登录失败", res?res.description:'');
                        return;
                    }
                    localStorage.setItem('userToken', res.data.token);
                    location.replace('/activity/web/wpIndex?random=' + Math.random());
                }
            });
            return false;
        }
    },
    mounted() {
    }
})



