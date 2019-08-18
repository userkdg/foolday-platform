var geocoder,map,marker = null;
var init = function() {
    var center = new qq.maps.LatLng(39.916527,116.397128);
    map = new qq.maps.Map(document.getElementById('tencent_map'),{
        center: center,
        zoom: 15
    });
    //调用地址解析类
    geocoder = new qq.maps.Geocoder({
        complete : function(result){
            vm.loc_callback(result.detail);
            map.setCenter(result.detail.location);
            var marker = new qq.maps.Marker({
                map:map,
                position: result.detail.location
            });
        }
    });
}

function codeAddress(address) {
    //通过getLocation();方法获取位置信息值
    geocoder.getLocation(address);
}