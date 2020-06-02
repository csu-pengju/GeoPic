
var index = 0;//每张图片的下标，
PhotoWall = function (options) {

}

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

//当移动到圆圈，则停滞对应的图片
PhotoWall.prototype.funny = function(lst,i){
    var me = this;
    lst[i].onmouseover = function () {
        me.changeImg(i)
    }
};

PhotoWall.prototype.autoPlay = function(){
    var me = this;
    if(me.index>5) {
        me.index=0;
    }
    // me.changeImg(me.index++); //不知道为什么用这句话会返回me.changeImg is not a function
    PhotoWall.prototype.changeImg(me.index++);
};



