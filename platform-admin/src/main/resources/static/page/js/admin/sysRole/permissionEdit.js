layui.use(['tree', 'util'], function(){
    console.log(layui)
    var tree = layui.tree
        ,layer = layui.layer
        ,util = layui.util
    var _data = []
    var roleId = $("#primaryKey").val()
    var getPermissionData = function(){
        return new Promise(function(resolve,reject){
            $.ajax({
                url: "/admin/sysRole/getPermissions",
                data:"roleId=" + roleId,
                type: "get",
                dataType: "json",
                success: function (res) {
                    resolve(res.data)
                },
                error: function (e) {
                    reject(e)
                }
            });
        })
    }
    function runGetPermission(){
        getPermissionData()
            .then(function(data) {
                _data = []
                data.forEach(function (item) {
                    var obj = {
                        title: item.name,
                        id: item.id,
                        spread: true,
                        children: []
                    }
                    // if(item.have){
                    //     obj.checked = true
                    // }else {
                    //     obj.checked = false
                    // }
                    if(item.available){
                        obj.disabled = false
                    }else{
                        obj.disabled = true
                    }
                    item.sysPermissionVOS.forEach(function (item1) {
                        var obj1 = {
                            title: item1.name,
                            id: item1.id
                        }
                        if(item1.have){
                            obj1.checked = true
                        }
                        if(item1.available){
                            obj1.disabled = false
                        }else{
                            obj1.disabled = true
                        }
                        obj.children.push(obj1)
                    })
                    _data.push(obj)
                })
                console.log(_data)
                //开启复选框
                tree.render({
                    elem: '#permissionBox'
                    ,data: _data
                    ,id: 'permissionTree'
                    ,showCheckbox: true
                });
            })
            .catch(function (err) {
                layer.msg('加载失败!', {
                    icon: 1,
                    time: 1000
                })
            })
    }
    runGetPermission()

    var submitPermission = function(data){
        return new Promise(function(resolve,reject){
            layer.msg('权限修改中....', {
                icon: 16
            })
            $.ajax({
                url: "/admin/sysRole/updatePermission",
                data:JSON.stringify(data),
                type: "post",
                dataType: "json",
                contentType:"application/json;charset=UTF-8",
                success: function (res) {
                    resolve(res)
                },
                error: function (e) {
                    reject(e)
                }
            });
        })
    }

    //按钮事件
    $("#confirmChange").on("click",function(){
        var checkedData = tree.getChecked('permissionTree'); //获取选中节点的数据
        console.log(checkedData);
        var params = {
            roleId: roleId,
            permissionVOS: []
        }
        var changePermissionData = []
        var checkedId = []
        var checkAndDisableId = []

        _data.forEach(function (item) {
            item.children.forEach(function (child) {
                var obj = {}
                obj.id = child.id
                if(child.checked === undefined || !child.checked){
                    obj.have = false
                }else{
                    obj.have = true
                }
                changePermissionData.push(obj)
            })
        })

        _data.forEach(function (item) {
            item.children.forEach(function (child) {
                if(child.checked === true && child.disabled) {
                    checkAndDisableId.push(child.id)
                }
            })
        })

        checkedData.forEach(function (item) {
            item.children.forEach(function (child) {
                checkedId.push(child.id)
            })
        })

        checkedId = checkedId.concat(checkAndDisableId)

        changePermissionData.forEach(function (item) {
            if(checkedId.includes(item.id)){
                item.have = true
            }else{
                item.have = false
            }
        })
        console.log(changePermissionData)
        params.permissionVOS = changePermissionData

        submitPermission(params).then(function (res) {
            console.log(res)
            if(res.errcode === 0){
                layer.msg('权限修改成功!', {
                    icon: 1,
                    time: 1000
                })
                runGetPermission()
            }else{
                layer.msg('权限修改失败!', {
                    icon: 1,
                    time: 1000
                })
            }
        }).catch(function (err) {
            console.log(err)
            layer.msg('发生未知错误!', {
                icon: 1,
                time: 1000
            })
        })

    })

});