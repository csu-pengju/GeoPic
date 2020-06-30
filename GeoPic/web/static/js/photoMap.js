var __amapAPIOK = false;
function initGoogleMap() {
    __amapAPIOK = true;
};
/**
 *
 * @param options
 * @constructor
 */

PhotoMap = function (options) {
    console.log("hello")
    var me = this;
    me.options = $.extend({
        width:"100%",
        height:"800px"
    },options);
    me._isdraging = false;
    me._init();
};
/**
 * 初始化函数
 * @private
 */
PhotoMap.prototype._init = function () {
    console.log("hello")
    var me = this;

    me.amap = new AMap.Map('map', {
        zoom:16,
        zooms:[4,20],//级别
        center: [112.926, 28.164166],//中心点坐标
    });
    me.getPhotosGPS();
};

/**
 * 从数据库中获取所有照片的路径和坐标
 */
PhotoMap.prototype.getPhotosGPS = function () {
    var me = this;
    $.ajax({
        type:"POST",
        dataType:"json",
        url:"/getPhotoGPSServlet",
        success:function (res) {
            var json = typeof res=='string'?JSON.parse(res):res;
            console.log(json.GPSAndPath.length);
            console.log(json.GPSAndPath[0].GPS);
            me.markerMap(json.GPSAndPath);
        }
    });
};

/**
 *
 * @param wkt：数据库返回的wkt格式的点数据
 * @returns {[]} ：符合高德地图标记的数组形式的坐标标记
 */
PhotoMap.prototype.getGPSPosition = function(wkt){
    var GPS = [];
    if(wkt){
        // console.log("wkt的length"+wkt.length);
        var data  = wkt.split("(");
        data =data[1].split(")");
        data = data[0].split(" ");
        GPS.push(parseFloat(data[0]));
        GPS.push(parseFloat(data[1]));
        console.log(GPS)
    }
    return GPS;
};

/**
 *
 * @param GPSAndPath
 * 用于在地图上标记图片。目前样式比较简单，后续我会修改滴
 */
PhotoMap.prototype.markerMap = function (GPSAndPath) {
    var me = this;
    var spots = [];
    var zoomStyleMapping1 = {
        4:1,
        5:1,
        6:1,
        7:1,
        8:1,
        9:0,
        10:0,
        11:0,
        12:0,
        13:0,
        14:0,
        15:0,
        16:0,
        17:0,
        18:0,
        19:0,
        20:0
    };
    for(var i=0;i<GPSAndPath.length;i+=1){

        console.log(GPSAndPath[i].GPS)
        var marker = new AMap.ElasticMarker({
            position:me.getGPSPosition(GPSAndPath[i].GPS),
            zooms:[4,20],
            styles:[{
                icon:{
                    img:GPSAndPath[i].Path,
                    size:[40,40],//可见区域的大小
                    ancher:[0,0],//锚点,相对于左上角
                    fitZoom:14,//最合适的级别
                    scaleFactor:2,//地图放大一级的缩放比例系数
                    maxScale:2,//最大放大比例
                    minScale:0.8,//最小放大比例,
                    zIndex:2
                },
                label:{
                    //content:"彭举",
                    offset:[-15,-5],
                    position:'TR',
                    minZoom:15
                }
            },{
                icon:{
                    img:GPSAndPath[i].Path,
                    size:[40,40],//可见区域的大小
                    ancher:[0,0],//锚点
                    fitZoom:4,//最合适的级别
                    scaleFactor:2,//地图放大一级的缩放比例系数
                    maxScale:2,//最大放大比例
                    minScale:0.8//最小放大比例
                },
                label:{
                    //content:GPSAndPath.length,
                   // offset:[65,-200],
                    offset:[0,0],
                    position:'TR'
                }
            }
            ],
            zoomStyleMapping:zoomStyleMapping1

        });
        spots.push(marker);
    }
    me.amap.add(spots);
    var mousedownEvent = marker.on("mousedown",function (e) {
        console.log(e)
    })


};


