var vm = new Vue({
    el: "#container",
    data: {
    	userId: "",
    	userName: "",
    	openid: "",
    	userPhone:"",
    	userIcon: "",
        createTime: ""
    },
    methods: {


    }
});

layui.use(['table', 'layer', "jquery", "form"], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.jquery;


    $("#select_role").submit(function (e) {
        var sel_val = $("input[name=sel_name]").val();
        
        var id = vm.id;
        table.reload("manager_list", {
            page: {
                curr: 1
            },
            where: {
                name: sel_val
            }
        })

        return false;
    })

    table.render({
        elem: '#user_list'
        , url: '/admin/Usr/findAll'
        , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , limit: 10,
        limits: [10],
        id: "manager_list"
        , cols: [[
            { type: "numbers", width: 80, title: '序号' },
            { field: 'userName', title: '用户名' },
            { toolbar: '#userIcon', title: '用户头像'},
            { toolbar: '#gender', title: '用户性别' },
            { field: 'createTime', title: '创建时间' }
        ]],
        page: true,
        parseData: function (res) {
            if (res.errCode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                          "code": res.code,
                          "data": res.data.content,
                          "count": res.data.totalElements,
                          "limit": 18
                      }
        }
    });

    $(document).on("click", ".layui-upload-img", function (e) {
        layer.open({
            type: 1,
            shade: true,
            title: false,
            area: ["600px", "350px"],
            content: "<img src='" + $(this).attr("src") + "' style='display: block;width:100%;height: 100%;max-width:100%'>"
        });
    });
});