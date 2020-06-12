/**
 * 用于照片墙
 * 目前已经实现照片信息的上传，照片信息的提取，照片信息（拍摄地点、时间、GPS、存储路径）存储到数据库
 * 未解决的问题：
 *      1. 将上传的照片本身的数据传给后台，总是有问题；
 *      2. 未实现读取已经上传的照片，后面会写的，只要第一个问题解决了
 *      3. 未实现关于照片中人脸信息的相关内容，后面再写
 */

//一些全局变量
var index = 0;//每张图片的下标，
var formatted_address ="";
var takenTime = "";
var geo="";
var photoData = "";
/**
 *
 * @param options
 * @constructor
 *
 */
PhotoWall = function (options) {
    var me = this;
    me.initPhotoWall();
};

/**
 * 图片轮播
 */
PhotoWall.prototype.imageScroll = function () {
    var me = this;
    var start = setInterval(me.autoPlay, 2000);//开始轮播，每秒换一张图
    $(".imgae-scroll").onmouseover = function () {
        clearInterval(start);//当鼠标悬停在图片上，停止轮播
    };
    $(".imgae-scroll").onmouseout = function () {
        start = setInterval(me.autoPlay, 2000);//当鼠标离开图片，开始轮播
    };
    var lst = $(".imgyuan")[0].getElementsByTagName('span');//得到所有的圆圈
    for (var i = 0; i < lst.length; i++) {
        me.funny(lst,i);
    }
};

/**
 * 改变图片时的操作
 */
PhotoWall.prototype.changeImg = function(){
    var me = this;
    var index = arguments[0];
    var list = $(".imgae-scroll")[0].getElementsByTagName("img");
    var list1 = $(".imgyuan")[0].getElementsByTagName("span");
    for (var i = 0;i<list.length;i++) {
        list[i].style.display = "none";
        list1[i].style.backgroundColor = "white";
    }
    list[index].style.display = "block";
    list1[index].style.backgroundColor="red";
};

/**
 * 当移动到圆圈，则停滞对应的图片
 */
PhotoWall.prototype.funny = function(lst,i){
    var me = this;
    lst[i].onmouseover = function () {
        me.changeImg(i)
    }
};

/**
 * 设置自动播放
 */
PhotoWall.prototype.autoPlay = function(){
    var me = this;
    if(me.index>5) {
        me.index=0;
    }
    // me.changeImg(me.index++); //不知道为什么用这句话会返回me.changeImg is not a function
    PhotoWall.prototype.changeImg(me.index++);
};
/**
 * 用于初始化显示照片墙
 */
PhotoWall.prototype.initPhotoWall = function(){
    var me = this;
    $.ajax({
        type:"POST",
        url:"/initPhotoWallServlet",
        // dataType:"json",
        data:"",
        success:function (res) {
            var json = typeof res=='string'?JSON.parse(res):res;
            for (var i = 0;i<json.photoPath.length;i++){
                // console.log(json.photoPath[i]);
                me.CreateImage(json.photoPath[i]);
            }
        }
    });
};

/**
 * 上传图片的入口
 */
PhotoWall.prototype.uploadPhotos = function () {
    var me = this;
    var files = document.getElementById("input_upload_driver").files;
    me.passPhotoData();
    for(var i = 0;i<files.length;i++){
        var file = files[i];
        me.getExifData(file);
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload=function (ev) {
            // console.log(this.result)
            me.CreateImage(this.result);
        }
    }
};

/**
 * 用于将读取的图片的文件名,base_64格式的数据传到后台
 */
PhotoWall.prototype.passPhotoData = function(){
    var me = this;
    // me.getFaceRect();
    var files = document.getElementById("input_upload_driver").files;
    console.log("我看看不弄了几次啊");
    for(var i = 0;i<files.length;i++){
        var file = files[i];
        var reader = new FileReader();
        var r = new FileReader();
        r.readAsDataURL(file);
        r.onloadend= function (ev) {
            photoData = this.result;
            // console.log(photoData);
            console.log(file.name);
            var photoDat = photoData.replace("data:image/jpeg;base64",file.name);
            photoDat = photoDat.replace("data:image/png;base64",file.name);
            photoDat = photoDat.replace("data:image/gif;base64",file.name);
            photoDat = photoDat.replace("data:image/bmp;base64",file.name);
            $.ajax({
                type:"POST",
                url:"/getPhotoDataServlet",
                data:photoDat,
                contentType : false,
                processData : false,  //必须false才会避开jQuery对 formdata 的默认处理
                cache : false,
                async:true,
                success:function (res) {
                    console.log("上传完成");
                    me.getFaceInfo(photoDat,file);
                }
            });

        }

    }
};
/**
 * 将上传的照片上传到face++ API to detect faces in photo
 * @param photoData emmm,没用,要删掉的
 * @param file  input获取的file
 */
PhotoWall.prototype.getFaceInfo = function(photoData,file){
    var me = this;
    me.faces = [];
    var ph = photoData.replace(file.name+",","");
    let data = new FormData();
    data.append('api_key', "ms3MvC2UjwJBlSs5wNTVj-3SXPPAURq3");
    data.append('api_secret', "P6wgCmBYbeFGRG76cwTOTe6k2V5jS1vY");
    data.append('image_file', file)
    var detectData ='api_key=ms3MvC2UjwJBlSs5wNTVj-3SXPPAURq3&api_secret=P6wgCmBYbeFGRG76cwTOTe6k2V5jS1vY'+
        '&image_file='+file;
    $.ajax({
        url:"https://api-cn.faceplusplus.com/facepp/v3/detect",
        type:"POST",
        data:data,
        cache: false,
        processData: false,
        contentType: false,
        // dataType:"json",
        success:function (res) {
            console.log(res);
            var json = typeof res=='string'?JSON.parse(res):res;
            var face_num = json.face_num;
            var faces = json.faces;
            me.faces = []
            // console.log(faces)
            if(face_num>0){
                for(var i = 0;i<face_num;i++){
                    me.faces[i] = faces[i];
                }
                var data = {}
                data["faces"] = me.faces
               me.uploadFaceInfo(data,file);
                console.log(data)
            }else{
                console.log("no faceInfo")
            }
        },
        error:function (err) {
            console.log(err)
        }
    });

};

/**
 * 将检测到的人脸信息传到后台
 * @param faces face++ 人脸检测返回的人脸信息:face_token和face_rectangle(top,left,height,width)
 */
PhotoWall.prototype.uploadFaceInfo = function(faces,file){
    var me = this;
    console.log(faces)
    console.log(typeof faces.toString())
    // console.log(faces[0])
    $.ajax({
        type:'POST',
        url:"/uploadFaceInfoServlet",
        dateType:'json',
        data:
            {
                "faces":JSON.stringify(faces),
                "file":file.name
            },
        success:function (res) {
            console.log(res);
        },
        error:function (err) {

        }
    });
};

PhotoWall.prototype.getFaceRect = function(cv,x,y,width,height){

};

/**
 *
 * @param file 从input中获取的参数
 * @param r 暂时没啥用，后面应该会删掉
 * 获取上传图片的元数据
 */
PhotoWall.prototype.getExifData= function(file){

    var me = this;
    var location = "";
    EXIF.getData(file,function () {
        var exifData = EXIF.pretty(this);
        console.log(exifData);
        if(exifData){
            // console.log(exifData);
            var lat = EXIF.getTag(this, "GPSLatitude");
            var lon = EXIF.getTag(this, "GPSLongitude");
            takenTime = EXIF.getTag(this,"DateTime");
            if(!takenTime){
                takenTime = "no timeInfo";
            }
            console.log(takenTime);
            if(lat&&lon){
                var lat1 = parseFloat(lat[0])+parseFloat(lat[1]/60)+parseFloat(lat[2]/3600);
                var lon1 =  parseFloat(lon[0])+parseFloat(lon[1]/60)+parseFloat(lon[2]/3600);
                location = lon1+","+lat1;
                geo = 'POINT('+lon1+' '+lat1+')';
                // console.log(geo);
                $.ajax({
                    type:"get",
                    url:"https://restapi.amap.com/v3/geocode/regeo",
                    data:{
                        key:"8b6b3261c4d70b409feaa273ada901f2",
                        location:location
                    },
                    async:true,
                    success:function (res) {
                        formatted_address = res.regeocode.formatted_address;
                        PhotoWall.prototype.passPhotoInfo(file);
                    }
                });
            }else{
                formatted_address = "no placeInfo";
                PhotoWall.prototype.passPhotoInfo(file);
            }


        }else {
            takenTime = "no timeInfo";
            geo = "no GPSInfo";
            formatted_address = "no placeInfo";
            PhotoWall.prototype.passPhotoInfo(file);

        }
    });

};

/**
 *
 * @param file
 * 将解析的图片数据传给后台：包括图片的文件名、图片的拍摄地点、拍摄时间、GPS坐标
 */
PhotoWall.prototype.passPhotoInfo = function(file){

    $.ajax({
        type:"POST",
        url:"/getThumbsServlet",
        // dataType: 'json',
        data:{
                "photoName":file.name,
                "takenPlace":formatted_address,
                "geo":geo,
                "takenTime":takenTime

        },
        async:true,
        success:function (res) {
            console.log(res);
        }
    });


};

/**
 *
 * @param res
 * @constructor
 * 根据上传的图片自动构造图片列表
 */
PhotoWall.prototype.CreateImage = function (res) {
    var me = this;
    // res = "../images/lunbo6.jpg";
    me.ul = $("#thumbs-ul").css({
        display:"inline",
        // width:300+"px",
        // height:300+"px"
    });
    var li = document.createElement('li');
    li.innerHTML='<a href="#"><img src="../images/lunbo6.jpg" /></a>';
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




