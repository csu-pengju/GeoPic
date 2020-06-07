var __amapAPIOK = false;
function initGoogleMap() {
    __amapAPIOK = true;
}
PhotoWall = function (options) {
    var me = this;
    me.options = $.extend({
        width:"100%",
        height:"800px"
    },options);
    me._isdraging = false;
    me._init();
    me.getPhotosGPS();
};

PhotoWall.prototype._init = function () {
    console.log("hello")
    var me = this;

    me.amap = new AMap.Map('map', {
        zoom:11,//级别
        center: [116.397428, 39.90923],//中心点坐标

    });
};

PhotoWall.prototype.markerMap = function () {
    var me = this;
    var GPSArray = me.getPhotosGPS();
    console.log(GPSArray);

}

PhotoWall.prototype.getPhotosGPS = function () {
    var me = this;
    console.log("是否在getPhotoGPS")
    var GPSres = [];
    $.ajax({
        type:"POST",
        dataType:"json",
        url:"/getPhotoGPSServlet",
        success:function (res) {
            var json = typeof res=='string'?JSON.parse(res):res;
            // console.log(JSON.parse(res.toString()));
            console.log(json.GPSAndPath[0].GPS);
        }

    });
}
