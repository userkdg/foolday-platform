var vm = new Vue({
    el: ".layui-form",
    data: {
        id: null,
    },
    methods: {
        loadDatas: function () {
            var id = getSearch("id");
            if (!id) {
                layer.msg("当前资讯不存在!");
                closeWin(true);
                return;
            }
            this.id = id;
            $.ajax({
                url: "/admin/Combo/findById",
                data: {
                    id: this.id
                },
                type: "get",
                dataType: "json",
                success: function(result) {
                    if (result && result.errcode == 0) {
                        var initData = result.data;
                        // $("[name='id']").val(initData.id);
                        // $("[name='scenicPicture']").val(initData.scenicPicture);
                        // $('#showPicture1').attr('src',initData.scenicPicture);
                        // $("[name='picture']").val(initData.picture);
                        // $('#showPicture').attr('src',initData.picture);
                        // $("[name='name']").val(initData.name);
                        // $("[name='eName']").val(initData.eName);
                        // $("[name='province']").val(initData.province);
                        // $("[name='city']").val(initData.city);
                        // $("[name='address']").val(initData.address);
                        // $("[name='sketch']").val(initData.sketch);
                        // $("[name='phone']").val(initData.phone);
                        // if(initData.popular == 1){
                        //     $('#popular').attr('checked','checked');
                        // }
                        // form.render(); // 重新绘制表单，让修改生效
                    } else {
                        layer.msg('数据加载异常！', new Function());
                    }
                },
                error:function(e) {
                    layer.msg('网络异常！', new Function());
                }
            });
        }
    },
    computed: {
    },
});

layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;
    var upload = layui.upload;

    vm.loadDatas();
    // function init() {
    //     defineSelect();
    //     getInitData();
    // }
    // init();

    // function defineSelect() {
    //     $.ajax({
    //         url: '/api/getProvince',
    //         dataType: 'json',
    //         type: 'get',
    //         success: function (result) {
    //             if (result.errcode == 0) {
    //                 var data = result.data;
    //                 $.each(data, function (index, item) {
    //                     $('#province').append(new Option(item.province)); // 下拉菜单里添加元素
    //                 })
    //                 form.render(); // 渲染 把内容加载进去
    //             }
    //         }
    //     });
    // }

    // form.on('select(city)', function (data) {
    //     var province = $("#province").val();
    //     $.ajax({
    //         url: '/api/getCity',
    //         dataType: 'json',
    //         type: 'post',
    //         data: {
    //             province: province
    //         },
    //         success: function (result) {
    //             if (result.errcode == 0) {
    //                 var data = result.data;
    //                 $('#city').empty();
    //                 $.each(data, function (index, item) {
    //                     $('#city').append(new Option(item.city)); // 下拉菜单里添加元素
    //                 })
    //                 form.render(); // 渲染 把内容加载进去
    //             }
    //         }
    //     });
    // })


    // var initData; // 回显的数据
    // function getInitData() {
    //     var primaryKey = $("#primaryKey").val();
    //     $.ajax({
    //         url: "/admin/hotel/getInfo",
    //         data: {
    //             id: primaryKey
    //         },
    //         type: "get",
    //         dataType: "json",
    //         success: function(result) {
    //             if (result && result.errcode == 0) {
    //                 initData = result.data;
    //                 $("[name='id']").val(initData.id);
    //                 $("[name='scenicPicture']").val(initData.scenicPicture);
    //                 $('#showPicture1').attr('src',initData.scenicPicture);
    //                 $("[name='picture']").val(initData.picture);
    //                 $('#showPicture').attr('src',initData.picture);
    //                 $("[name='name']").val(initData.name);
    //                 $("[name='eName']").val(initData.eName);
    //                 $("[name='province']").val(initData.province);
    //                 $("[name='city']").val(initData.city);
    //                 $("[name='address']").val(initData.address);
    //                 $("[name='sketch']").val(initData.sketch);
    //                 $("[name='phone']").val(initData.phone);
    //                 if(initData.popular == 1){
    //                     $('#popular').attr('checked','checked');
    //                 }
    //                 form.render(); // 重新绘制表单，让修改生效
    //             } else {
    //                 layer.msg('数据加载异常！', new Function());
    //             }
    //         },
    //         error:function(e) {
    //             layer.msg('网络异常！', new Function());
    //         }
    //     });
    // }

    // var uploadInst1 = layui.upload.render({
    //     elem: '#showPicture1',
    //     url: '/file/upload',
    //     auto: false,
    //     size: 2048, // 限制文件大小，单位KB
    //     choose: function (obj) {
    //         obj.preview(function (index, file, result) {
    //             $('#showPicture1').attr('src', result); // 图片链接（base64）
    //         });
    //     },
    //     done: function (res) {
    //         if (res.errcode == 0) {
    //             $('#scenicPicture').val(res.data);
    //         } else {
    //             return layer.msg('上传失败');
    //         }
    //     },
    //     error: function () {
    //         var showText = $('#showText');
    //         showText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
    //         showText.find('.demo-reload').on('click', function () {
    //             uploadInst.upload();
    //         });
    //     }
    // });

    // var uploadInst = upload.render({
    //     elem: '#showPicture',
    //     url: '/file/upload',
    //     auto: false,
    //     size: 500, // 限制文件大小，单位KB
    //     choose: function (obj) {
    //         obj.preview(function (index, file, result) {
    //             $('#showPicture').attr('src', result); // 图片链接（base64）
    //         });
    //     },
    //     done: function (res) {
    //         if (res.errcode == 0) {
    //             $('#picture').val(res.data);
    //             doSubmit();
    //         } else {
    //             return layer.msg('上传失败');
    //         }
    //     },
    //     error: function () {
    //         var showText = $('#showText');
    //         showText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
    //         showText.find('.demo-reload').on('click', function () {
    //             uploadInst.upload();
    //         });
    //     }
    // });


    // form.on("submit(edit)", function (data) {
    //     uploadInst1.upload();
    //     uploadInst.upload();
    //     return false;
    // });


    // function doSubmit() {
    //     var name =  $('#name').val();
    //     var eName = $('#eName').val();
    //     var picture =$('#picture').val();
    //     var scenicPicture = $('#scenicPicture').val();
    //     var address = $('#address').val();
    //     var sketch = $('#sketch').val();
    //     var phone = $('#phone').val();
    //     var province = $('#province').val();
    //     var city = $('#city').val();
    //     var popular =  $('#popular').val();
    //     var a = checkName();
    //     if (!a) {
    //         return false;
    //     }
    //     var id = $('#primaryKey').val();
    //     $.ajax({
    //         url: "/admin/hotel/edit",
    //         data: {
    //             id : id,
    //             name: name,
    //             eName: eName,
    //             picture: picture,
    //             scenicPicture: scenicPicture,
    //             address: address,
    //             sketch: sketch,
    //             phone: phone,
    //             province : province,
    //             city : city,
    //             popular: popular
    //         },
    //         type: "post",
    //         async: false,
    //         dataType: "json",
    //         success: function(result) {
    //             if (result && result.errcode == 0) {
    //                 layer.msg(result.errmsg, {icon: 1, time: 1000}, function () {
    //                     var index = parent.layer.getFrameIndex(window.name);
    //                     parent.location.reload();
    //                     parent.layer.close(index);
    //                 });
    //             } else {
    //                 layer.msg("操作失败", {icon: 2, time: 1000});
    //             }
    //         },
    //         error: function(e) {
    //             layer.msg('网络异常,请稍后再试', new Function());
    //         }
    //     });
    //     return false;
    // }

    // function checkName() {
    //     var flag = false;
    //     var city = $("#city").val();
    //     if (city != null && city.length > 0) {
    //         $.ajax({
    //             url: "/admin/hotel/checkName",
    //             data: {
    //                 city: city
    //             },
    //             type: "get",
    //             async: false,
    //             dataType: "json",
    //             success: function(result) {
    //                 if (result && result.code == 1) {
    //                     layer.tips("该名称已存在，请勿重复添加", '#name', {tips: [3, 'red']});
    //                     $('name').focus();
    //                 } else {
    //                     flag = true;
    //                 }
    //             },
    //             error:function(e) {
    //                 layer.msg('网络异常！', new Function());
    //             }
    //         });
    //     } else {
    //         flag = true;
    //     }
    //     return flag;
    // };
});