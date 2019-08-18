/*
 * 弹窗框架
 * ©copyright Besam
 */

 // Loading.show(),显示, Loading.hide(), 隐藏
var Loading = (function(element) {
    var loading;
    function createLoading() {
        var $loading = $(`
            <div class="besam-loading-toggle">
                <div class="besam-box">
                    <div class="besam-loading">
                        <div class="besam-circle"></div>
                        <div class="besam-text">加载中...</div>
                    </div>
                </div>
            </div>
                
        `);
        $(element).append($loading);
    }
    createLoading.prototype.show = function() {
        $('.besam-loading-toggle').show();
    }
    createLoading.prototype.hide = function() {
        $('.besam-loading-toggle').hide();
    }
    loading = new createLoading();
    return loading;
})('body');

 // Message.show(),显示, Message.hide(), 隐藏
var Message = (function(element) {
    var message;
    function createMessage() {
        var $loading = $(`
            <div class="besam-message-toggle">
                <div class="besam-box">
                    <div class="besam-message">
                        <div class="besam-message-box">
                            <h4 class="besam-message-title"></h4>
                            <p class="besam-message-text"></p>
                        </div>
                    </div> 
                </div>
            </div>
                
        `);
        $(element).append($loading);
    }
    createMessage.prototype.show = function(title, msg) {
        $('.besam-message-title').text(title);
        $('.besam-message-text').text(msg);
        $('.besam-message-toggle').show();
        setTimeout(function() {
            $('.besam-message-toggle').hide();
        }, 1500)
    }
    message = new createMessage();
    return message;
})('body');

var win = {
    showLoading: Loading.show,
    hideLoading: Loading.hide,
    showMessage: Message.show
}