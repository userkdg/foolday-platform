layui.use(['table', 'layer', "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    var tableIns;

    tableIns = table.render({
        elem: '#dataView',
        url: '/admin/integral/findAll',
        title: '会员积分表',
        method: 'get',
        toolbar: '#toolbar',
        page: true,
        limit: 5,
        limits: [5, 15, 25],
        cols: [
            [
                {type: "numbers", width: 80, align: "center", title: '序号'},
                {field: 'username', title: '会员名', align: 'center'},
                {field: 'phone', title: '会员手机号', align: 'center'},
                {field: 'totalIntegral', title: '总积分', align: 'center'},
                {title: '操作', fixed: "right", align: 'left', toolbar: '#operator_item', width: "25%"}
            ]
        ],
        parseData: function (res) {
            if (res.errcode != 0) {
                layer.msg(res.description);
                return {};
            }
            return {
                "code": res.errcode,
                "data": res.data,
                "count": res.count,
                "limit": 5
            }
        }
    });

    table.on('tool(data)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'pictureView':
                layer.open({
                    type: 2,
                    title: "查看",
                    content: "/admin/integral/look?id=" + data.id,
                    resize: false,
                    area: ['625px', '700px']
                });
                break;
        }
        ;
    });

    form.on("submit(query)", function (data) {
        var url;
        var type = $("#type").val();
        var value = $("#value").val();
        if(type == "0")
            url = "/admin/userAdmin/findByPhone?value="+value;
        else
            url = "/admin/userAdmin/findByName?value="+value;
        // 表格重新加载
        tableIns = table.render({
            elem: '#dataView',
            url: url,
            title: '会员积分表',
            method: 'get',
            toolbar: '#toolbar',
            page: true,
            limit: 5,
            limits: [5, 15, 25],
            cols: [
                [
                    {field: 'id', title: '会员ID号', align: 'center', width: 280, sort: true},
                    {field: 'username', title: '会员名', align: 'center', width: 180},
                    {field: 'phone', title: '会员手机号', align: 'center', width: 180},
                    {field: 'totalIntegral', title: '总积分', align: 'center', width: 180},
                    {title: '操作', fixed: "right", align: 'left', toolbar: '#operator_item', width: 160}
                ]
            ],
            parseData: function (res) {
                if (res.errcode != 0) {
                    layer.msg(res.errmsg);
                    return {};
                }
                return {
                    "code": res.errcode,
                    "data": res.data,
                    "count": res.count,
                    "limit": 5
                }
            }
        });
        return false;
    });
});