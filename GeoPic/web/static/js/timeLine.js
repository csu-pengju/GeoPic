/**
 *
 * @param optiond
 * @constructor
 */
TimeLine = function (option) {
    var  me = this;
    me.getAllTimeLinePhotos();
};
/**
 *
 */
TimeLine.prototype.getAllTimeLinePhotos = function(){
    var me = this;
    $.ajax({
        url:"/getTimeLinePhotoPathServlet",
        type:"POST",
        dataType:"json",
        success:function (res) {
            var json = typeof res=='string'?JSON.parse(res):res;
            var data = json.data;

            var count = 0;
            for(var key in data){
                var time = key;
                var photos = data[key];

                if(count%2==0){
                    me.createTimeLineDiv(time,photos,"timeline-left")
                }else{
                    me.createTimeLineDiv(time,photos,"timeline-right")
                }
                count+=1;
                console.log(time,photos.length)
            }
        },
        error:function (err) {
            console.log(err)
        }
    });
};
TimeLine.prototype.createTimeLineDiv = function(time,photos,classes){
    var me = this;
    me.ul = $(".timeline-ul-box");
    me.li = $("<li></li>").appendTo(me.ul).addClass(classes);
    me.a = $('<a onclick="TimeLine.prototype.showTimeline(this)"></a>').appendTo(me.li).attr({
        "photo-src":photos
    });
    me.a.text(time) ;
};
TimeLine.prototype.showTimeline = function (res) {
    var me = this;
    $(".timelineModel").css({
        display:"block"
    });
    var photos_src= $(res).attr("photo-src");
    console.log(photos_src)
    if(photos_src.indexOf(",")>-1){
        var imgSrcs = photos_src.split(",");
        for (var i = 0;i<imgSrcs.length;i++){
            me.createImage(imgSrcs[i]);
        }
        // me.createImage()
    }else if(photos_src){
        me.createImage(photos_src)
    }

};

TimeLine.prototype.createImage = function (imgsrc) {
    var me = this;
    console.log(imgsrc)
    me.ul = $(".timelineModel-photos");
    me.li = $("<li></li>").appendTo(me.ul).css({
        display: "inline-block"
    }).addClass("timelineLi");
    me.img = $('<img src='+imgsrc+'>').appendTo(me.li).css({
        // float:"left",
        width:"200px",
        height:"200px",
        padding:"10px"
    });
};

TimeLine.prototype.closeTimeLineModal= function () {
    var me = this;
    $(".timelineModel").css({
        display:"none"
    });
    $(".timelineLi").remove();
};



