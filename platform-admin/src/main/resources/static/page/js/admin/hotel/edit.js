layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var nameTemp;
    var pictrue;

    function init() {
        defineSelect();
        getInitData();
    }

    init();

    function defineSelect() {
        $.ajax({
            url: '/api/getProvince',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#province').append(new Option(item.province)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
        $.ajax({
            url: '/api/getCity',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#city').append(new Option(item.city)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    form.on('select(city)', function (data) {
        var province = $("#province").val();
        $.ajax({
            url: '/api/getCity',
            dataType: 'json',
            type: 'post',
            data: {
                province: province
            },
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $('#city').empty();
                    $.each(data, function (index, item) {
                        $('#city').append(new Option(item.city)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    })


    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/hotel/getInfo",
            data: {
                id: primaryKey
            },
            type: "get",
            dataType: "json",
            success: function (result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    nameTemp = initData.name;
                    pictrue = initData.scenicPicture;
                    $("[name='id']").val(initData.id);
                    $("[name='scenicPicture']").val(initData.scenicPicture);
                    $('#showPicture').attr('src', initData.scenicPicture);
                    $("[name='name']").val(initData.name);
                    $("[name='eName']").val(initData.eName);
                    $("[name='province']").val(initData.province);
                    $("[name='city']").val(initData.city);
                    $("[name='food']").val(initData.food);
                    $("[name='characteristic']").val(initData.characteristic);
                    $("[name='address']").val(initData.address);
                    $("[name='sketch']").val(initData.sketch);
                    $("[name='sequence']").val(initData.sequence);
                    $("[name='phone']").val(initData.phone);
                    if (initData.popular == 1) {
                        $('#popular').attr('checked', 'checked');
                    }
                    form.render(); // 重新绘制表单，让修改生效
                } else {
                    layer.msg('数据加载异常！', new Function());
                }
            },
            error: function (e) {
                layer.msg('网络异常！', new Function());
            }
        });
    }

    var uploadInst = layui.upload.render({
        elem: '#showPicture',
        url: '/file/upload',
        auto: false,
        size: 2048, // 限制文件大小，单位KB
        choose: function (obj) {
            obj.preview(function (index, file, result) {
                $('#showPicture').attr('src', result); // 图片链接（base64）
            });
        },
        done: function (res) {
            $('#scenicPicture').val(res.data);
            doSubmit();
        },
        error: function () {
            var showText = $('#showText');
            showText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            showText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });

    form.on("submit(edit)", function (data) {
        var scenicPicture = $('#scenicPicture').val();
        if (pictrue != scenicPicture || scenicPicture == null) {
            uploadInst.upload();
        } else {
            doSubmit();
        }
        return false;
    });


    function doSubmit() {
        var name = $('#name').val();
        var eName = $('#eName').val();
        if (name != nameTemp) {
            var a = checkName();
            if (!a) {
                return false;
            }
        }
        var scenicPicture = $('#scenicPicture').val();
        var address = $('#address').val();
        var sketch = $('#sketch').val();
        var phone = $('#phone').val();
        var province = $('#province').val();
        var city = $('#city').val();
        var food = $('#food').val();
        var sequence = $('#sequence').val();
        var characteristic = $('#characteristic').val();
        var popular = $('#popular').val();
        if (($("#popular").prop('checked') != true)) {
            popular = 0;
        }
        var id = $('#primaryKey').val();
        $.ajax({
            url: "/admin/hotel/edit",
            data: {
                id: id,
                name: name,
                eName: eName,
                food: food,
                characteristic: characteristic,
                scenicPicture: scenicPicture,
                address: address,
                sketch: sketch,
                food: food,
                sequence: sequence,
                characteristic: characteristic,
                phone: phone,
                province: province,
                city: city,
                popular: popular
            },
            type: "post",
            async: false,
            dataType: "json",
            success: function (result) {
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
            error: function (e) {
                layer.msg('网络异常,请稍后再试', new Function());
            }
        });
        return false;
    }

    function checkName() {
        var flag = false;
        var city = $("#city").val();
        if (city != null && city.length > 0) {
            $.ajax({
                url: "/admin/hotel/checkName",
                data: {
                    city: city
                },
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.code == 1) {
                        layer.tips("该名称已存在，请勿重复添加", '#name', {tips: [3, 'red']});
                        $('name').focus();
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
});