/**
 *
 * @constructor
 */
PhotoQuery = function () {
    var me = this;
    me.controlQueryCondition();
};

PhotoQuery.prototype.controlQueryCondition = function () {
    var me = this;
    var obj = $(".selectC").val();
    if(obj=="timeQuery"||obj=="pleaseChoose"){
        console.log("dd")
        $(".timeQuery-box").show();
        $(".placeQuery-box").hide();
        $(".semanticQuery-box").hide();
        $(".integratedQuery-box").hide();
    }else if(obj=="placeQuery"){
        $(".timeQuery-box").hide();
        $(".placeQuery-box").show();
        $(".semanticQuery-box").hide();
        $(".integratedQuery-box").hide();
    }else if(obj=="semanticQuery"){
        $(".timeQuery-box").hide();
        $(".placeQuery-box").hide();
        $(".semanticQuery-box").show();
        $(".integratedQuery-box").hide();
    }else if(obj=="integratedQuery"){
        $(".timeQuery-box").show();
        $(".placeQuery-box").show();
        $(".semanticQuery-box").show();
        $(".integratedQuery-box").hide();
    }

};

PhotoQuery.prototype.showQueryRes = function (res) {
    var me = this;
    me.ul = $("#photos-ul").css({
        display:"inline",
        // width:300+"px",
        // height:300+"px"
    });
    me.myli = $("<li></li>").appendTo(me.ul).css({
        display:"inline",
        padding:22+"px",
        float:'left',

    });
    me.myA = $('<a href="'+res+' download =dsd.jpg"></a>').appendTo(me.myli);

    me.myImg = $('<img src="'+res+'"/>').appendTo(me.myA).css({
        width:180+"px",
        height:180+"px"
    }).addClass("myImg");
    // console.log(me.myImg)
};