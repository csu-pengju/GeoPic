/**
 *
 * @constructor
 */
PhotoQuery = function () {
    var me = this;
    me.controlQueryCondition();
    me.setDefaultDate();
};

PhotoQuery.prototype.setDefaultDate = function(){
  var me = this;
  console.log("hh")
  var now = new Date();
  //格式化日，如果小于9，前面补0
  var day = ("0" + now.getDate()).slice(-2);
  //格式化月，如果小于9，前面补0
  var month = ("0" + (now.getMonth() + 1)).slice(-2);
  //拼装完整日期格式
  var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
  $(".toDate-input").val(today);
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

PhotoQuery.prototype.getQueryPhotoPath = function(){
    var me = this;
    // console.log("ddada")
    document.getElementById("photos-ul").innerHTML = "";
    me.condition = $(".selectC").val();
    console.log(me.condition)
    me.startTime = $(".fromDate-input").val();
    me.endTime = $(".toDate-input").val();
    me.address = $(".place-input").val();
    me.photoLabels =$(".photoLabel-input").val();
    me.faceLabels = $(".faceLabel-input").val();
    console.log(me.photoLabels);
    console.log(me.faceLabels)
    me.geo = "";
    // console.log(startTime,endTime,address,photoLabels,faceLabels)
    if(me.address!=""){
        me.geo = me.getGPSFromAddress(me.address);
        console.log("dd"+me.geo)
    }else{
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"/getQueryPhotoPathServlet",
            data:{
                "condition":me.condition,
                "startTime":me.getTimeStampfromDate(me.startTime),
                "endTime":me.getTimeStampfromDate(me.endTime),
                "address":me.address,
                "geo":me.geo,
                "photoLabels":me.photoLabels,
                "faceLabels":me.faceLabels
            },
            async:true,
            success:function (res){
                var json = typeof res=='string'?JSON.parse(res):res;

                for(var i = 0;i<json.photoPath.length;i++){
                    me.showQueryRes(json.photoPath[i]);
                    console.log("he"+json.photoPath[i]);
                }
            },
        });
    }

};
 PhotoQuery.prototype.getTimeStampfromDate = function(time){
     var me = this;
     console.log(time);
     if(time.indexOf("-")>0){
         return time;
     }
     var temp = time.split("/");

     var stampTime = "";
     if(temp.length==3){
         stampTime = temp[0]+"-"+temp[1]+"-"+temp[2];
     }
     console.log("hello"+stampTime)
     return time;
 };

PhotoQuery.prototype.getGPSFromAddress = function(address){
    var me = this;
    var geo = "";
    $.ajax({
        type:"get",
        url:"https://restapi.amap.com/v3/geocode/geo",
        data:{
            key:"8b6b3261c4d70b409feaa273ada901f2",
            address:address
        },
        async:true,
        success:function (res) {
            var json = typeof res=='string'?JSON.parse(res):res;
            var geocodes= typeof json['geocodes']=='string'?JSON.parse(json['geocodes']):json['geocodes'];
            var GPS = geocodes[0].location.split(",");
            me.geo = "POINT("+GPS[0]+" "+GPS[1]+")";
            console.log(me.geo);
            $.ajax({
                type:'POST',
                // dataType:"json",
                url:'/getQueryPhotoPathServlet',
                data:{
                    "condition":me.condition,
                    "startTime":me.getTimeStampfromDate(me.startTime),
                    "endTime":me.getTimeStampfromDate(me.endTime),
                    "address":me.address,
                    "geo":me.geo,
                    "photoLabels":me.photoLabels,
                    "faceLabels":me.faceLabels
                },
                async:true,
                success:function (res){
                    var json = typeof res=='string'?JSON.parse(res):res;

                    for(var i = 0;i<json.photoPath.length;i++){
                        me.showQueryRes(json.photoPath[i]);
                        console.log("he"+json.photoPath[i]);
                    }
                }
            });
        }
    });
    return geo;
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
    me.myA = $('<a href="'+res+'"></a>').appendTo(me.myli);

    me.myImg = $('<img src="'+res+'"/>').appendTo(me.myA).css({
        width:180+"px",
        height:180+"px"
    }).addClass("myImg");
    // console.log(me.myImg)
};