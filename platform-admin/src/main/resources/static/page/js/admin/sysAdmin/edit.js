layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var phoneTmp;

    function checkPone() {
        var flag = false;
        var phone = $("#phone").val();
        if (phone != null && phone.length > 0) {
            $.ajax({
                url: "/admin/checkPhone",
                data: "phone=" + phone,
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 100) {
                        layer.tips(result.errmsg, '#phone', {tips: [3, 'red']});
                        $('phone').focus();
                    } else {
                        flag = true;
                    }
                },
                error: function (e) {
                    layer.msg('网络异常！', new Function());
                }
            });
        } else {
            flag = true;
        }
        return flag;
    };

    function defineSelectRole() {

        $.ajax({
            url: '/admin/sysRole/findAll',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0){
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#sysRoleId').append(new Option(item.name)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }
    function defineSelectHotel() {
        $.ajax({
            url: '/admin/hotel/getHotelName',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#hotelId').append(new Option(item.name,item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }
    defineSelectRole();
    defineSelectHotel();

    function init() {
        getInitData();
    }
    init();

    var initData; // 回显的数据
   function getInitData() {
        return new Promise(function(resolve,reject){
            var primaryKey = $("#primaryKey").val();
            $.ajax({
                url: "/admin/sysAdmin/getInfo",
                data: "id=" + primaryKey,
                type: "get",
                dataType: "json",
                success: function(result) {
                    if (result && result.errcode == 0) {
                        initData = result.admin;
                        $("#primaryKey").val(initData.id);
                        $("#username").val(initData.username);
                        $("#phone").val(initData.phone);
                        $("#remarks").val(initData.remarks);
                        phoneTmp = initData.phone;
                        form.render(); // 重新绘制表单，让修改生效
                    } else {
                        layer.msg('数据加载异常！', new Function());
                    }
                    resolve(phoneTmp)
                },
                error:function(e) {
                    layer.msg('网络异常！', new Function());
                }
            });
        })
    }

    form.on("submit(edit)", function (data) {
        doSubmit(phoneTmp);
        return false;
    });
    var doSubmit = function(phoneTmp) {
        var username =  $('#username').val();
        var phone =$('#phone').val();
        var remarks = $('#remarks').val();
        var hotelId = $('#hotelId').val();
        var sysRoleId = $('#sysRoleId').val();
        var password = $("#password").val();
        var id = $('#primaryKey').val();
        if(phone != null && phone != phoneTmp) {
            var a = checkPone();
            if (!a) {
                return false;
            }
        }
        $.ajax({
            url: "/admin/sysAdmin/update",
            data: JSON.stringify({
                id: id.toString(),
                username: username,
                phone: phone,
                remarks: remarks,
                hotelId: hotelId,
                sysRoleId: sysRoleId,
                password: password
            }),
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            dataType: "json",
            success: function(result) {
                if (result && result.errcode == 0) {
                    layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.location.reload();
                        parent.layer.close(index);
                    });
                } else {
                    layer.msg("操作失败", {icon: 2, time: 1000});
                }
            },
            error: function(e) {
                layer.msg('网络异常,请稍后再试', new Function());
            }
        });
        return false;
    }
});
