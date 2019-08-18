

var params = getQuery(document.location.toString());
if(!params.code) {
    var userInfo = JSON.parse(localStorage.getItem('userInfo'));
    if(!userInfo) {
        var reUrl = 'http://community.jystu.cn/activity/web/wpIndex';
        var appId = ''
        document.location.replace('https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + appId + '&redirect_uri=' + reUrl + '&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect');
    }
} else {
    // 发送 code 到后台
}

/*!
 * 裁取url的查询参数
 * 传入url字符串，返回对象形式的查询参数
 */
function getQuery(url) {
    var query = {};
    var params = url.split('?')[1].split('&');
    params.forEach(function(val) {
        query[val.split('=')[0]] = val.split('=')[1];
    })
    return query;
}