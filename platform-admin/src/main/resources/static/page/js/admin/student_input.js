var vm = new Vue({
    el: "#student_input",
    data: {
        filepath: "",
        filename: ""
    },
    methods: {
        del: function () {
            vm.filename = "";
        }
    }
})

layui.use(["jquery", "form", "upload"], function () {
    var $ = layui.jquery;
    var upload = layui.upload;

    upload.render({
        elem: "#choose_file",
        url: "/activity/admin/uploadFile",
        accept: "file",
        exts: "xls|xlsx",
        done: function (res) {
            if (!res || res.errcode != 0) {
                layer.msg(res ? res.description : "请求出错！");
                return;
            }
            vm.filepath = res.data.filepath;
            vm.filename = res.data.filename;
            layer.msg("文件上传成功!");
        }
    });

    $("#input_stu").submit(function (e) {
        $.ajax({
            url: "/activity/admin/stuInput",
            methods: "POST",
            data: {
                filepath: vm.filepath
            },
            success: function (res) {
                if (!res || res.errcode != 0) {
                    layer.msg(res ? res.description : "上传失败");
                    return false;
                }
                layer.msg("导入成功!!!");
                setTimeout(function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }, 500);
            }
        })
        return false;
    });

})