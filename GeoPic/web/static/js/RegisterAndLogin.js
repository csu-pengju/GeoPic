/**
 * 注册登录
 *
 */
RegisterAnfLogin = function (options) {

}
RegisterAnfLogin.prototype.register = function () {
    var me = this;
    var username = $(".usernameInput").val();
    var password = $(".passwordInput").val();
    var passwordSure = $(".passwordInputSure").val();
    console.log(username);
    console.log(password);
    if(password==passwordSure){
        me.submitRegisterDataToLoginServlet(username,password);
    }else{
        alert("请确认密码");
    }
};

RegisterAnfLogin.prototype.submitRegisterDataToLoginServlet = function(username,password){
    $.ajax({
        type:"POST",
        // dataType:"json",
        url:"/RegisterServlet",
        data:{
            "username":username,
            "password":password
        },
        success:function (res) {
            if(res=="200"){
                $(".login").show();
            }else {
                alert("用户名已存在")
            }
            console.log("有数据吗",res);
        }
    });
};
RegisterAnfLogin.prototype.loginDirect = function () {
    $(".login").show();
};

RegisterAnfLogin.prototype.loginCheck = function () {
    var me = this;
    var username = $(".username").val();
    var password = $(".password").val();
    console.log(username);
    console.log(password);
    $.ajax({
        type: "POST",
        // dataType:"json",
        url: "/UserLoginServlet",
        data: {
            "username": username,
            "password": password
        },
        success: function (res) {
            console.log(res);
            if (res == "200") {
                console.log("登录成功")
                window.location.href="static/html/photoWall.html"
            } else {
                alert("用户名或密码不正确")
            }
        }
    });
};