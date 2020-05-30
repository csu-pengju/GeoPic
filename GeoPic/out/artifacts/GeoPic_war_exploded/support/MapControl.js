/* global ol */

var WEB_MAP_API_READY = {
    "amap": false,
    "google": false,
    "tianditu": false
};


/**
 * 
 * @param {json} options 地图初始化选项 <br>
 * 
 * {    <br>
 *  div: "divid to render the mapcontrol" <br>
 *  width: 800,<br>
 *  height:600,<br>
 *  center: ol.proj.transform([112.9,28.3],"EPSG:4326","EPSG:3857"),<br>
 *  zoom: 10,<br>
 *  apis:{<br>
 *      google:"",<br>
 *      tianditu:"",<br>
 *      amap:"",<br>
 *      bing:""<br>
 *  },<br>
 *  
 *  backmap:"osm"  <br>
 * }<br>
 * 
 * back</pre>map参数及含义：<br>
 *  osm: openstreetap<br>
 *  google_satellite: 谷歌影像<br>
 *  google_roadmap: 谷歌地图（道路，房屋）<br>
 *  google_terrain: 谷歌地形<br>
 *  google_hybrid: 谷歌混合地图<br>
 *  amap_standard: 高德标准地图<br>
 *  amap_satellite: 高德影像<br>
 *  amap_traffic: 高德交通<br>
 * 
 * @returns {MapControl对象}
 */
MapControl = function (options) {
    var me = this;

    me.__id_prefix = "m_" + Math.random() + "_";
    me.__id = 0;
    me.options = $.extend(true, {
        width: "800px",
        height: "600px",
        center: ol.proj.transform([112.9, 28.3], "EPSG:4326", "EPSG:3857"),
        zoom: 4,
        backmap: "osm",
        apis: {
        }
    }, options);

    me._projUtils = new ProjectionUtils();
    me.options.backmap = me.options.backmap.toUpperCase();
    if (!me.options.div) {
        throw "a id of div must be specified to render the mapcontrol!";
    }
    me.el = $("#" + me.options.div).css({
        width: me.options.width ,
        height: me.options.height ,
        position: "relative"
    });

    me.busydiv = $("<div>").appendTo(me.el).css({
        "z-index": 10000,
        "display": "none"
    }).addClass("map-full").html("忙着呢");
    me.busydiv.on("mouseover mousepress mouserelease", function (e) {
        e.stopPropagation();
    });


    me.olDiv = $("<div>").appendTo(me.el).css({
        "z-index": 9000
    }).addClass("map-full").attr({
        id: me._createID()
    });





    me._initOlMap();


};

/**
 * 内部函数,创建一个在mapcontrol里唯一的id<br>
 * 不建议外部使用者调用<br>
 * @returns {String}
 */
MapControl.prototype._createID = function () {
    var me = this;
    me.__id++;
    return me.__id_prefix + me.__id;
};


MapControl.prototype._initOlMap = function () {
    var me = this;

    me.osmLayer = new ol.layer.Tile({
        source: new ol.source.OSM()
    });

    me.sketchSource = new ol.source.Vector();
    me.sketchLayer = new ol.layer.Vector({
        source: me.sketchSource,
        style: new ol.style.Style({
            fill: new ol.style.Fill({
                color: 'rgba(255, 127, 127, 0.2)'
            }),
            stroke: new ol.style.Stroke({
                color: '#ffcc33',
                width: 2
            }),
            image: new ol.style.Circle({
                radius: 7,
                fill: new ol.style.Fill({
                    color: '#ffcc33'
                })
            })
        })
    });

    me.olMap = new ol.Map({
        layers: [
            me.osmLayer,
            me.sketchLayer
        ],
        target: me.olDiv.attr("id"),
        view: new ol.View({
            center: me.options.center,
            zoom: me.options.zoom,
            constrainResolution: true
        })
    });

    if (me.options.backmap === "OSM") {
        me.osmLayer.setVisible(true);
    } else {
        me.osmLayer.setVisible(false);
    }

    me.setBackMap(me.options.backmap);


    var view = me.olMap.getView();
    view.on("change:center", function () {
        me._updateBackMap();
    });
    view.on("change:resolution", function () {
        me._updateBackMap();
    });


    me._initInteractions();

};

/**
 * 
 * @param {type} op 设置当前绘制类型：Point,LineString,Polygon
 *  如果op不是三者中的一个，则设置为非绘制状态
 * @returns {undefined}
 */
MapControl.prototype.setDrawOperation = function (op) {
    var me = this;
    me._deactiveAllInteractions();
    if (["Point", "LineString", "Polygon"].indexOf(op) < 0) {
        return;
    }
    me.olMap.addInteraction(me._drawInteractions[op]);
};

/**
 * 内部函数，不建议外部调用者使用<br>
 * 为MapControl增加矢量绘制、捕获、修改工具<br>
 * @returns {undefined}
 */
MapControl.prototype._initInteractions = function () {
    var me = this;
    me._initDrawInteractions();

    me._snapInteraction = new ol.interaction.Snap({
        source: me.sketchSource
    });
    me.olMap.addInteraction(me._snapInteraction);
    me._modifyInteraction = new ol.interaction.Modify({
        source: me.sketchSource
    });
    me.olMap.addInteraction(me._modifyInteraction);
};

/**
 * 内部函数，不建议外部调用者使用<br>
 * 为MapControl增加矢量绘制(Point,LineString,Polygon)工具<br>
 * @returns {undefined}
 */
MapControl.prototype._initDrawInteractions = function () {
    var me = this;
    var types = ["Point", "LineString", "Polygon"];
    me._drawInteractions = {};
    for (var i = 0; i < types.length; i++) {
        me._drawInteractions[types[i]] = new ol.interaction.Draw({
            source: me.sketchSource,
            type: types[i]
        });
    }
};

/**
 * 内部函数，不建议外部调用者使用<br>
 * 清楚所有的互操作工具<br>
 * @returns {undefined}
 */
MapControl.prototype._deactiveAllInteractions = function () {
    var me = this;
    for (var key in me._drawInteractions) {
        me.olMap.removeInteraction(me._drawInteractions[key]);
    }
};



/**
 * 设置背景地图
 * @param {type} bkmap 背景地图类型，大小写均可<br>
 *  当前支持：<br>
 *  osm: openstreetap<br>
 *  google_satellite: 谷歌影像<br>
 *  google_roadmap: 谷歌地图（道路，房屋）<br>
 *  google_terrain: 谷歌地形<br>
 *  google_hybrid: 谷歌混合地图<br>
 *  amap_standard: 高德标准地图<br>
 *  amap_satellite: 高德影像<br>
 *  amap_traffic: 高德交通<br>
 * @returns {undefined}
 */
MapControl.prototype.setBackMap = function (bkmap) {
    var me = this;
    bkmap = bkmap.toUpperCase();

//    if (bkmap === me.options.backmap) {
//        return;
//    }

    me.options.backmap = bkmap;
    if (me._bkDiv) {
        me._bkDiv.remove();
        me.bkMap = null;
    }

    if (bkmap === "OSM") {
        me.osmLayer.setVisible(true);
        return;
    }

    me.osmLayer.setVisible(false);

    me._bkDiv = $("<div>").appendTo(me.el).css({
        "z-index": 1
    }).addClass("map-full").attr({
        "id": me._createID()
    });

    var maptype = me.options.backmap.split("_")[0];

    if (maptype === "GOOGLE") {
        if (!me.options.apis.google ||
                $.trim(me.options.apis.google) === "") {
            throw "Google map api key must be provied!";
        }
        if (WEB_MAP_API_READY.google) {
            me._createGoogleMap();
        } else {
            $.getScript("https://maps.googleapis.com/maps/api/js?key=" + me.options.apis.google, function () {
                WEB_MAP_API_READY.google = true;
                me._createGoogleMap();
            });
        }
        return;
    }


    if (maptype === "AMAP") {
        if (!me.options.apis.amap ||
                $.trim(me.options.apis.amap) === "") {
            throw "Google map api key must be provied!";
        }
        if (WEB_MAP_API_READY.amap) {
            me._createAMap();
        } else {
            $.getScript("https://webapi.amap.com/maps?v=1.4.15&key=" + me.options.apis.amap, function () {
                WEB_MAP_API_READY.amap = true;
                me._createAMap();
            });
        }
        return;
    }
};


MapControl.prototype._createGoogleMap = function () {
    var me = this;
    var view = me.olMap.getView();
    var center = ol.proj.transform(view.getCenter(), "EPSG:3857", "EPSG:4326");
    me.bkMap = new google.maps.Map(me._bkDiv[0], {
        center: {
            lat: center[1],
            lng: center[0]},
        zoom: view.getZoom(),
        mapTypeId: me.options.backmap.toLowerCase().split("_")[1]
    });
    me._updateBackMapGoogle();

    var i = 0;
    var timer = setInterval(() => {
        me._clearBackMapControls();
        i++;
        if (i >= 15) {
            clearInterval(timer);
        }
    }, 100);
};



MapControl.prototype._clearBackMapControls = function () {
    var me = this;

    // remove google controls
    me.el.find(".gmnoprint").remove();
    me.el.find(".gm-fullscreen-control").remove();
    me.el.find("div > div > div:nth-child(3) > a").remove();

};


MapControl.prototype._updateBackMap = function () {
    var me = this;


    var mapgroup = me.options.backmap.split("_")[0];
    if (!me.bkMap) {
        return;
    }

    if (mapgroup === "GOOGLE") {
        me._updateBackMapGoogle();
        return;
    }

    if (mapgroup === "AMAP") {
        me._updateBackMapAMap();
    }
};


MapControl.prototype._updateBackMapGoogle = function () {
    var me = this;
    var view = me.olMap.getView();
    var center = view.getCenter();
    var zoom = view.getZoom();
    center = ol.proj.transform(center, "EPSG:3857", "EPSG:4326");
    if (me.options.backmap !== "GOOGLE_SATELLITE") {
        center = me._projUtils.wgs84togcj02(center[0], center[1]);
    }
    me.bkMap.setOptions({
        center: {
            lng: center[0],
            lat: center[1]
        },
        zoom: zoom
    });
};



MapControl.prototype._createAMap = function () {
    var me = this;
    var view = me.olMap.getView();
    var center = ol.proj.transform(view.getCenter(), "EPSG:3857", "EPSG:4326");

    var layers = [];
    if (me.options.backmap === "AMAP_SATELLITE") {
        layers.push(new AMap.TileLayer.Satellite());
    } else if (me.options.backmap === "AMAP_STANDARD") {
        layers.push(new AMap.TileLayer());
    } else if (me.options.backmap === "AMAP_TRAFFIC") {
        layers.push(new AMap.TileLayer());
        layers.push(new AMap.TileLayer.Traffic({
            'autoRefresh': true,
            'interval': 180
        }));
    }
    me.bkMap = new AMap.Map(me._bkDiv.attr("id"), {
        zoom: view.getZoom(),
        layers: layers
    });
    me._updateBackMapAMap();

    var i = 0;
    var timer = setInterval(() => {
        me._clearBackMapControls();
        i++;
        if (i >= 15) {
            clearInterval(timer);
        }
    }, 100);
};

MapControl.prototype._updateBackMapAMap = function () {
    var me = this;
    var view = me.olMap.getView();
    var center = view.getCenter();
    var zoom = view.getZoom();
    center = ol.proj.transform(center, "EPSG:3857", "EPSG:4326");
    center = me._projUtils.wgs84togcj02(center[0], center[1]);
    me.bkMap.setZoomAndCenter(zoom, center);
};
MapControl.prototype.getMap = function () {
  return
};