
function convertElect(electronicCard) {
    var statusDesc = "";
    if (electronicCard == 1) {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs">制作</p>';
    }  else {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">不制作</p>';
    }
    return statusDesc;
};

function convertPromotion(promotion) {
    var statusDesc = "";
    if (promotion == 1) {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs">推荐</p>';
    }  else {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">不推荐</p>';
    }
    return statusDesc;
};

function convertRefund(refund) {
    var statusDesc = "";
    if (refund == 1) {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs">可退款</p>';
    }  else {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">不可退款</p>';
    }
    return statusDesc;
};


function convertTop(top) {
    var statusDesc = "";
    if (refund == 1) {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs">置顶</p>';
    }  else {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">不置顶</p>';
    }
    return statusDesc;
};

function convertType(type) {
    var statusDesc = "";
    if (type == 0) {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs">乐享套餐</p>';
    }  else {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">普通套餐</p>';
    }
    return statusDesc;
};

function convertStauts(status) {
    var statusDesc = "";
    if (status == 0) {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs">已上架</p>';
    }  else {
        statusDesc = '<p class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger">已下架</p>';
    }
    return statusDesc;
};

var vm = new Vue({
    el: "#container",
    data: {
        hotals: [],
        hotalId: "",
        img: "",
        upload_result: "上传中...",
        chooseHotals: [],
        name: "",
        combo:{}
    },
    methods: {
        initCombo: function() {
            vm.combo = {};
            vm.img = "";
            vm.chooseHotals = [];
            vm.upload_result="上传中...";
        }
        ,
        clear: function () {
            this.img = "";
            this.upload_result = "上传中...";
        },
        delSpec: function (index) {
            vm.chooseHotals.splice(index, 1)
        }
    }
})

layui.use(['table', 'layer', "jquery", "util", "form", "upload"], function () {
    var table = layui.table;
    var form = layui.form;
    var util = layui.util;
    var $ = layui.jquery;
    var upload = layui.upload;


    //设置内容，isAppendTo为TRUE是为追加内容
    var ue = UE.getEditor('comboDetails');
    function setContent(text) {
        UE.getEditor('comboDetails').ready(function () {
            UE.getEditor('comboDetails').execCommand('insertHtml', text)
        });
    }

    function getContent() {
        return UE.getEditor('comboDetails').getContent();
    }

    $.ajax(({
        url: "/admin/hotel/getHotelName",
        method: "GET",
        dataType: "json",
        success: function (res) {
            console.log(">>>>>>", res);
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错");
                return;
            }

            vm.hotals  = res.data;
        }
    }));

    form.on("select(hotalId)", function (obj) {
        var index = obj.value;
        for (var idx in vm.chooseHotals) {
            if(vm.chooseHotals[idx].id == vm.hotals[index].id) {
                layer.msg("不可重复添加！");
                return;
            }
        }
        vm.chooseHotals.push(vm.hotals[index]);
    })

    upload.render({
        elem: "#choose_img",
        url: "/file/upload",
        before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            vm.upload_result = "上传中...";
        },
        before: function (obj) {
            obj.preview(function (index, file, result) {
                vm.img = result;
            });
        },
        done: function (res) {
            console.log(">>>>>>res", res);
            if (res && res.errcode == 0) {
                vm.upload_result = "上传成功!";
                vm.img = res.data;
            } else {
                vm.upload_result = "上传失败!!!";
            }
        }
    });

    form.on("submit(add_combo)", function (obj) {
        vm.combo={};
        var data = obj.field;
        var ids = vm.chooseHotals.map(function (hotal) {
            return hotal.id;
        }).join(",");
        data.comboId= ids;
        data.comboPicture = vm.img;
        data.comboDetails=getContent();
        data.type =$("#comboType").val();
        var electronicCard = $('#electronicCard').val();
        if( $('#electronicCard').prop('checked')!=true){
            electronicCard = 0;
        }

        var refund = $('#refund').val();
        if( $('#refund').prop('checked')!=true){
            refund = 0;
        }

        var top = $('#top').val();
        if( $('#top').prop('checked')!=true){
            top = 0;
        }

        $.ajax({
            url: "/admin/Combo/save",
            method: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                console.log(res);
                res = JSON.parse(res);
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.errmsg : "请求出错");
                    return false;
                }

                location.reload();

            }
        });

        return false;
    })

    form.on("submit(update_combo)", function (obj) {
        var data = obj.field;
        var ids = vm.chooseHotals.map(function (hotal) {
            return hotal.id;
        }).join(",");
        data.comboId= ids;
        data.comboPicture = vm.img;
        data.comboDetails=getContent();
        data.type =$("#comboType").val();
        var electronicCard = $('#electronicCard').val();
        if( $('#electronicCard').prop('checked')!=true){
            electronicCard = 0;
        }

        var refund = $('#refund').val();
        if( $('#refund').prop('checked')!=true){
            refund = 0;
        }

        var top = $('#top').val();
        if( $('#top').prop('checked')!=true){
            top = 0;
        }

        $.ajax({
            url: "/admin/Combo/comboUpdate?id="+vm.combo.id,
            method: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                console.log(res);
                res = JSON.parse(res);
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.errmsg : "请求出错");
                    return false;
                }

                location.reload();

            }
        });

        return false;
    })

    table.render({
        elem: '#dataView',
        url: '/admin/Combo/findAll',
        title: '套餐列表',
        cellMinWidth: 100,  //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        method: 'post',
        toolbar: '#toolbar',
        page: true,
        limit: 15,
        limits: [15],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {toolbar: '#img_detail', title: '封面图片'},
                {field: 'comboName', title: '套餐名称'},
                {field: 'introduction', title: '套餐简介'},
                {field: 'comboDetails', title: '套餐详情'},
                {field: 'type', title: '套餐类型',templet: '<div>{{convertType(d.type)}}</div>'},
                {field: 'vaildity', title: '有效期天数'},
                {field: 'peopleNumber', title: '可使用人数'},
                {field: 'price', title: '套餐价格'},
                {field: 'top', title: '是否置顶' ,templet: '<div>{{convertTop(d.top)}}</div>'},
                {field: 'promotion', title: '是否推荐', templet:'<div>{{convertPromotion(d.promotion)}}</div>'},
                {field: 'refund', title: '是否可退款', templet:'<div>{{convertRefund(d.refund)}}</div>'},
                {field: 'electronicCard', title: '是否制作乐享卡', templet: '<div>{{convertElect(d.electronicCard)}}</div>'},
                {field: 'status', title: '上|下架', templet:'<div>{{convertStauts(d.status)}}</div>'},
                {field: 'preferentialInformation', title: '优惠信息'},
                {title: '操作', fixed: "right", align: 'center', toolbar: '#operator_item',width:"20%"}
            ]
        ],
        parseData: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.errmsg : "请求出错");
                return {};
            }

            res.data = res.data.map(function (item) {
                item.releaseTime = util.toDateString(item.releaseTime, "yyyy-MM-dd HH:mm:ss");
                return item;
            });

            return {
                "code": res.errcode,
                "data": res.data,
                "count": res.count,
                "size": 15
            }
        }
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        console.log("---obj---", obj);

        switch (obj.event) {
            case 'edit':
                vm.initCombo();
                vm.combo=obj.data;
                console.log(vm.combo);
                vm.chooseHotals=vm.combo.comboId.split(",")
                    .map(o=>vm.hotals.find(h => h.id === +o))
                    .filter(o => o);
                $("#comboType").val(vm.combo.type);
                $("#electronicCard").prop("checked", vm.combo.electronicCard === 1);
                $("#promotion").prop("checked", vm.combo.promotion === 1);
                $("#refund").prop("checked", vm.combo.refund === 1);
                $("#top").prop("checked", vm.combo.top === 1);
                vm.img = vm.combo.comboPicture;
                vm.upload_result="";
                layer.open({
                    title: "添加活动",
                    content: $(".add_combos"),
                    type: 1,
                    area: ["700px", "550px"]
                });
                setTimeout(function () {
                    layui.form.render("select");
                    setContent(vm.combo.comboDetails);
                    form.render('select');
                    form.render("checkbox")
                }, 500);
                break;
            case 'delete':
                layer.confirm('确认删除吗？', function (confirmIndex) {
                    layer.close(confirmIndex);
                    $.ajax({
                        url: "/admin/Combo/delCombo?id=" + data.id,
                        data: {
                            ids: data.id
                        },
                        type: "get",
                        dataType: "json",
                        success: function (result) {
                            if (!result || result.errcode != 0) {
                                layer.msg(res ? res.errmsg : '网络异常,请稍后再试');
                                return;
                            }
                            location.reload();
                        }
                    });
                });
                break;
        };
    });

    $(".modal").click(function () {
        vm.initCombo();
        layer.open({
            title: "添加活动",
            content: $(".add_combos"),
            type: 1,
            area: ["700px", "550px"]
        });
        form.render("select");
    })

});