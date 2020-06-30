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
var api_key = "ms3MvC2UjwJBlSs5wNTVj-3SXPPAURq3";
var api_secret = "P6wgCmBYbeFGRG76cwTOTe6k2V5jS1vY";
var faceset_token = "2d7ddb438c720087b2d4e95c22535d41";
var outer_id = "GeoPic";

/**
 *
 * @param options
 * @constructor
 *
 */
PhotoWall = function (options) {
    var me = this;
    me.searchRes = [];
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

};

/**
 * 创建faceset,用来保存检测出的每张人脸的face_token,用于后续的人脸搜索。
 * 只需要调用一次获取faceset,已调用，后续不需在调用此函数，只需要使用全局变量faceset_token（FaceSet的标识）
 */
PhotoWall.prototype.createFaceSet = function(){

    let data = new FormData();
    data.append('api_key', "ms3MvC2UjwJBlSs5wNTVj-3SXPPAURq3");
    data.append('api_secret', "P6wgCmBYbeFGRG76cwTOTe6k2V5jS1vY");
    data.append('outer_id',"GeoPic")
    $.ajax({
        url:"https://api-cn.faceplusplus.com/facepp/v3/faceset/create",
        type:"POST",
        data:data,
        cache: false,
        processData: false,
        contentType: false,
        success:function (res) {
            console.log(res)
        },
        error:function (err) {
            console.log(err)
        }
    });
};

/**
 * 这个函数存在的意义是我测试时将所有的能用到的含有人物的照片都用过了，所以对于后面我上传的每张图片都没法进行人物标签的测试
 */
PhotoWall.prototype.removeFaceToken = function(){

    let data = new FormData();
    data.append('api_key', "ms3MvC2UjwJBlSs5wNTVj-3SXPPAURq3");
    data.append('api_secret', "P6wgCmBYbeFGRG76cwTOTe6k2V5jS1vY");
    data.append('outer_id',"GeoPic");
    data.append("face_tokens","RemoveAllFaceTokens")
    $.ajax({
        url:" https://api-cn.faceplusplus.com/facepp/v3/faceset/removeface",
        type:"POST",
        data:data,
        cache: false,
        processData: false,
        contentType: false,
        success:function (res) {
            console.log(res)
        },
        error:function (err) {
            console.log(err)
        }
    });
};
/**
 * 用于将读取的图片的文件名,base_64格式的数据传到后台
 */
PhotoWall.prototype.passPhotoData = function(){
    var me = this;
    me.isExist = false;
    // me.getFaceRect();
    var files = document.getElementById("input_upload_driver").files;

    for(var i = 0;i<files.length;i++){
        var file = files[i];
        var r = new FileReader();
        r.readAsDataURL(file);
        r.onloadend= function (ev) {
            photoData = this.result;

            var photoDat = photoData.replace("data:image/jpeg;base64",file.name);
            photoDat = photoDat.replace("data:image/png;base64",file.name);
            photoDat = photoDat.replace("data:image/gif;base64",file.name);
            photoDat = photoDat.replace("data:image/bmp;base64",file.name);
            $.ajax({
                type:"POST",
                url:"/getPhotoDataServlet",
                data:photoDat,
                async:false,
                contentType : false,
                processData : false,  //必须false才会避开jQuery对 formdata 的默认处理
                cache : false,
                success:function (res) {
                    var json = typeof res=='string'?JSON.parse(res):res;
                    //如果数据库中没有这张照片，那么将这张照片上传到数据库，同时检测人脸信息
                    if(json.success){
                        console.log(json.success)

                        me.uploadPhotosToDB(file);
                        me.isExist = true;
                    }else{
                        console.log(json.message);
                        console.log(json.success)
                    }
                },
                error:function (err) {
                    console.log(err)
                }
            });

        }
    }
};
/**
 * 将照片上传至数据库
 * @param file  获取input中的file
 */
PhotoWall.prototype.uploadPhotosToDB = function(file){
    var me = this;

    me.getExifData(file);
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload=function (ev) {
        // console.log(this.result)
        me.CreateImage(this.result);
    }
    // for(var i = 0;i<files.length;i++){
    //     var file = files[i];
    //     me.getExifData(file);
    //     var reader = new FileReader();
    //     reader.readAsDataURL(file);
    //     reader.onload=function (ev) {
    //         // console.log(this.result)
    //         me.CreateImage(this.result);
    //     }
    // }
}
/**
 * 将上传的照片上传到face++ API to detect faces in photo
 * @param photoData emmm,没用,要删掉的
 * @param file  input获取的file
 */
PhotoWall.prototype.getFaceInfo = function(file){
    var me = this;
    me.faces = [];
    //var ph = photoData.replace(file.name+",","");
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
            me.faces = [];
            me.face_tokens=[];
            if(face_num>0){
                me.searchRes = [];
                for(var i = 0;i<face_num;i++){

                    //从FaceSet集合中搜索与检测出的人脸最相似的照片，若相似度大于75，我们认为这是同一个人

                    //这里我采用的同步方式，不知道后面后不会造成堵塞，可能会的吧
                    me.searchFace(faces[i].face_token);
                    if(me.searchRes.length>0){
                        console.log("faceset中已有此人物")
                        //me.face_tokens.push(faces[i].face_token);
                    }else{
                        me.addFace_tokenToFaceSet(faces[i].face_token);
                        me.faces.push(faces[i]);
                    }
                }
                console.log(me.searchRes)
                if(me.faces.length>0||me.searchRes.length>0){
                    var data = {};
                    data["faces"] = me.faces;
                    me.uploadFaceInfo(data,file,me.searchRes);
                }
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
 * 用于人脸检测
 * @param face_token 人脸的标识face_token
 */
PhotoWall.prototype.searchFace = function(face_token){
    var me = this;
    var searchRes = [];
    var data = new FormData();
    data.append("api_key",api_key);
    data.append("api_secret",api_secret);
    data.append("face_token",face_token);
    data.append("faceset_token",faceset_token);
    $.ajax({
        url:"https://api-cn.faceplusplus.com/facepp/v3/search",
        type:"POST",
        data:data,
        cache:false,
        processData:false,
        contentType:false,
        async:false,
        success:function (res) {
            var json = typeof res=='string'?JSON.parse(res):res;
            console.log(json);
            for(var i = 0;i<json.results.length;i++){
                var confidence = json.results[i].confidence;
                var face_token = json.results[i].face_token.toString();
                //这里数值比较的方式需要注意，不能直接比较，需要使用eval函数，置信度75不知道低不
                if(eval(confidence)>eval(75)){
                    me.searchRes.push(face_token);
                    break;
                }
            }
            return me.searchRes;
        },
        error:function (err) {
            console.log(err)
            return err;
        }
    });
    //return searchRes;
}

/**
 * 将人脸的face_token添加至FaceSet
 * @param face_token :检测出的图片中人脸的唯一标识face_token
 */
PhotoWall.prototype.addFace_tokenToFaceSet = function(face_token){
    var me = this;
    var data = new FormData();
    data.append("api_key",api_key);
    data.append("api_secret",api_secret);
    data.append("faceset_token",faceset_token);
    data.append("face_tokens",face_token);
    $.ajax({
        url:"https://api-cn.faceplusplus.com/facepp/v3/faceset/addface",
        type:"POST",
        data:data,
        cache:false,
        processData:false,
        contentType:false,
        success:function (res) {
            console.log(res)
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
PhotoWall.prototype.uploadFaceInfo = function(faces,file,face_tokens){
    var me = this;
    //console.log("faces IN ");
    console.log(faces.faces.length);
    if(faces.faces.length==0){

        me.uploadPhotoFaceId(file,"",face_tokens)
    }else{
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
                var json = typeof res=='string'?JSON.parse(res):res;

                var dir = "../data/faces/";
                var facesData = json.facesName;
                var facesPath = json.facesPath;
                console.log(facesPath)
                me.uploadPhotoFaceId(file,facesPath,face_tokens);
                me.facesData = facesData;
                me.facesPath = facesPath;
                me.count = 0;
                if(me.facesData.length>0){
                    me.showFaceModal(facesData[me.count],me.facesPath[me.count]);
                }
                // for(var i = 0 ;i<facesData.length;i++){
                //     me.showFaceModal(facesData[i],facesPath[i]);
                // }

            },
            error:function (err) {
            }
        });
    }


};

PhotoWall.prototype.uploadPhotoFaceId = function(file,facesPath,face_tokens){
    var me = this;
    var photoPath = "../data/photos/"+file.name;
    var facesPath = typeof facesPath =='undefined'? "":facesPath;
    var faceTokens = typeof face_tokens=='undefined'?"":face_tokens;

    $.ajax({
        url:"/uploadPhotoLabelAndFaceIdServlet",
        type:"POST",
        data:{
            "type":"faceId",
            "photoPath": photoPath,
            "facesPath":facesPath.toString(),
            "faceTokens":faceTokens.toString()
        },
        success:function (res) {
            console.log(res)
        },
        error:function (err) {
            console.log(err)
        }

    });

};

PhotoWall.prototype.showFaceModal=function(facesData,facesPath){
    var me = this;
    //这样直接设置图片路径有问题，因为浏览器对于静态资源的加载不是同步的，所以我们直接传入人脸的base64数据，这个数据由后台返回
    var base64 = "data:image/jpeg;base64,"+facesData;
    console.log("你执行了几次啊s"+facesPath)

    $("#faceImg").attr("src",base64);
    $(".modal").css({
        display:"block"
    });

    $("#cancelInputFaceLabel").click(function () {
        $(".modal").css({
            display:"none"
        });
        if(me.count+1<me.facesData.length){
            me.count +=1;
            me.showFaceModal(me.facesData[me.count],me.facesPath[me.count]);
        }
    });
    $("#sureInputFaceLabel").off("click").on('click', function () {
        me.handelFaceLabel(facesPath);
    });

};

PhotoWall.prototype.inputFacelabel = function(facesPath){
    var me = this;


};

PhotoWall.prototype.handelFaceLabel= function(facesPath){
    var me = this;
    var faceLabel = $("#facelabelText").val();
    // console.log(facesPath)
    $.ajax({
        url:"/upLoadFaceLabelServlet",
        type:"POST",
        dataType:"json",
        data:{
            "faceLabel":faceLabel,
            "facePath":facesPath
        },
        async:false,
        success:function (res) {
            console.log(res);
            $(".modal").css({
                display:"none"
            });
            $("#faceImg").attr("src","");
            $("#facelabelText").val("");
            if(me.count+1<me.facesData.length){
                me.count +=1;
                me.showFaceModal(me.facesData[me.count],me.facesPath[me.count]);
            }

        },
        error:function (err) {
            console.log(err)
            $(".modal").css({
                display:"none"
            });
            if(me.count+1<me.facesData.length){
                me.count +=1;
                me.showFaceModal(me.facesData[me.count],me.facesPath[me.count]);
            }
        }
    });
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
    var me = this;
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
            me.getFaceInfo(file);
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
    me.myA = $('<a onclick="PhotoWall.prototype.showPhotoDetail(this)"></a>').appendTo(me.myli);

    me.myImg = $('<img src="'+res+'"/>').appendTo(me.myA).css({
        width:180+"px",
        height:180+"px"
    }).addClass("myImg");

};

PhotoWall.prototype.showPhotoDetail = function (res) {
    var a =$(res)[0];
    var img = $(a).children();
    var imgSrc = $(img[0]).attr("src");
    var photoDeail = new PhotoDetail(imgSrc);
};






