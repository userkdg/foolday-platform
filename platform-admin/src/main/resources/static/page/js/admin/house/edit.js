layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;
    var nameTemp;

    function init() {
        defineSelect();
        getInitData();
    }

    init();

    function defineSelect() {
        $.ajax({
            url: '/admin/hotel/getHotelName',
            dataType: 'json',
            type: 'get',
            success: function (result) {
                if (result.errcode == 0) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        $('#hotelId').append(new Option(item.name, item.id)); // 下拉菜单里添加元素
                    })
                    form.render(); // 渲染 把内容加载进去
                }
            }
        });
    }

    var initData; // 回显的数据
    function getInitData() {
        var primaryKey = $("#primaryKey").val();
        $.ajax({
            url: "/admin/house/getInfo",
            data: {
                id: primaryKey
            },
            type: "get",
            dataType: "json",
            success: function (result) {
                if (result && result.errcode == 0) {
                    initData = result.data;
                    nameTemp = initData.name;
                    $("[name='id']").val(initData.id);
                    $("[name='hotelId']").val(initData.hotelId);
                    $("[name='name']").val(initData.name);
                    $("[name='bedType']").val(initData.bedType);
                    $("[name='picture']").val(initData.picture);
                    $('#showPicture').attr('src', initData.picture);
                    if (initData.isAirport == 1) {
                        $('#isAirport').attr('checked', 'checked');
                    };
                    if (initData.isBreakfast == 1) {
                        $('#isBreakfast').attr('checked', 'checked');
                    };
                    if (initData.status == 1) {
                        $('#status').attr('checked', 'checked');
                    };
                    if (initData.isCancelTime == 1) {
                        $('#isCancelTime').attr('checked', 'checked');
                    };
                    $("[name='area']").val(initData.area);
                    $("[name='price']").val(initData.price);
                    $("[name='content']").val(initData.content);
                    $("[name='residence']").val(initData.residence);

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

    var uploadInst = upload.render({
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
            if (res.errcode == 0) {
                $('#picture').val(res.data);
                doSubmit();
            } else {
                return layer.msg('上传失败');
            }
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
        var picture = $('#picture').val();
        if (picture == null || picture.length == 0) {
            uploadInst.upload();
            return false;
        } else {
            doSubmit();
            return false;
        }
    });

    function doSubmit() {
        var name = $('#name').val();
        if (name != nameTemp) {
            var a = checkName();
            if (!a) {
                return false;
            }
        }
        var bedType = $('#bedType').val();
        var picture = $('#picture').val();
        var area = $('#area').val();
        var content = $('#content').val();
        var residence = $('#residence').val();
        var price = $('#price').val();
        var isAirport = $('#isAirport').val();
        var isBreakfast = $('#isBreakfast').val();
        var isCancelTime = $('#isCancelTime').val();
        if (($("#isAirport").prop('checked') != true)) {
            isAirport = 0;
        }
        if (($("#isBreakfast").prop('checked') != true)) {
            isBreakfast = 0;
        }
        if (($("#isCancelTime").prop('checked') != true)) {
            isCancelTime = 0;
        }
        var status = $('#status').val();
        if (($("#status").prop('checked') != true)) {
            status = 0;
        }
        var id = $('#primaryKey').val();
        $.ajax({
            url: "/admin/house/edit",
            data: {
                id: id,
                name: name,
                bedType: bedType,
                picture: picture,
                area: area,
                content: content,
                residence: residence,
                isCancelTime: isCancelTime,
                status: status,
                price: price,
                isAirport: isAirport,
                isBreakfast: isBreakfast
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
        var name = $("#name").val();
        var hotelId = $('#hotelId').val();
        if (name != null && name.length > 0) {
            $.ajax({
                url: "/admin/house/checkName",
                data: {
                    name: name,
                    id: hotelId
                },
                type: "get",
                async: false,
                dataType: "json",
                success: function (result) {
                    if (result && result.errcode == 400) {
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