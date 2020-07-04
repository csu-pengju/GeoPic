/**
 * 用于用户点击照片墙照片，弹出照片详情的模态框
 * @constructor
 */
PhotoDetail = function (options) {
    let me = this;
    $(".photoDetailModal").css({
        display:"block"
    });

    //用户点击右上角的关闭按钮，则关闭照片详情模态框
    $(".cancelPhotoLabelEdit").click(function () {
        $(".photoDetailModal").css({
            display:"none"
        });
        $(".photoInfo-takenTime").text("拍摄时间 ：");
        $(".photoInfo-takenPlace").text("拍摄地点：");
        $(".myFace").remove();
        $(".FaceLi").remove();
        $(".inputPhotoLabel").val("");
        $(".inputPhotoLabel").attr({"readonly":"readonly"});

    });
    //设置照片详情界面的照片
    $(".photoDetailModal-content-originPhoto").attr("src",options);

    //点击编辑标签按钮则标签input中可以进行输入修改
    $(".btnModifyPhotoLabel").click(function () {
       $(".inputPhotoLabel").removeAttr("readonly")
    });
    me.getPhotoDetail(options);

    //更新用户修改的标签
    $(".sureModifyOp").click(function () {
        me.updatePhotoLabel(options);
    });
};

PhotoDetail.prototype.getPhotoDetail = function (photoPath) {
    console.log(photoPath)
    var me = this;
    $.ajax({
        url:"/getPhotoDetailServlet",
        type:"POST",
        dataType:"json",
        data:{
          "photoPath":photoPath
        },
        success:function (res) {
            var json = typeof res=='string'?JSON.parse(res):res;
            var facesPathAndLabel = json.facesId;
            //console.log(facesPathAndLabel.length)
            var takenPlace = json.takenPlace;
            var takenTime = json.takenTime;
            var photoLabels = json.photoLabels;
            console.log(photoLabels)
            me.setPhotoTimeAndPlaceAndLabel(takenPlace,takenTime,photoLabels);
            for(var i = 0;i<facesPathAndLabel.length;i++){
                console.log(facesPathAndLabel[i].facePath)
                me.createFaceImg(facesPathAndLabel[i].facePath)
            }
        },
        error:function (err) {
            console.log(err)
        }
    })
};

PhotoDetail.prototype.setPhotoTimeAndPlaceAndLabel = function(takenPlace,takenTime,photoLabels){
    var me = this;
    console.log(takenTime,takenPlace)
    if(takenTime.indexOf('9999')>-1){
        takenTime = "no time Info";
    }
    var timeTxt = $(".photoInfo-takenTime").text();
    var placeTxt = $(".photoInfo-takenPlace").text();
    $(".photoInfo-takenTime").text(timeTxt+takenTime);
    $(".photoInfo-takenPlace").text(placeTxt+takenPlace);
    //这里关于input的值的修改必须用val(),用途attr属性不行
    $(".inputPhotoLabel").val(photoLabels);
    console.log(photoLabels)
};

PhotoDetail.prototype.updatePhotoLabel = function(photoPath){
    var me = this;
    var photoLabels = $(".inputPhotoLabel").val();
    console.log(photoLabels)
    $.ajax({
       url:"/uploadPhotoLabelAndFaceIdServlet",
       type:"POST",
       dataType:"json",
       data:{
           "type":"photoLabel",
           "photoLabels":photoLabels,
           "photoPath":photoPath
       },
        success:function (res) {
            console.log(res)

            $(".photoDetailModal").css({
                display:"none"
            });
            console.log("dad")
            // $.message('成功');
        },
        error:function (err) {
            console.log(err)
            alert("更新照片标签失败！");
        }
    });

};


PhotoDetail.prototype.createFaceImg = function (facePath) {
    var me = this;

    me.ul = $(".faces-list").css({
        display:"inline",
    });

    me.myli = $("<li></li>").appendTo(me.ul).css({
        display:"inline",
        padding:10+"px",

    }).addClass("FaceLi");
    me.myImg = $('<img src="'+facePath+'"/>').appendTo(me.myli).css({
        width:100+"px",
        height:100+"px"
    }).addClass("myFace");
};

