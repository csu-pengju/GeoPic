<%--
  Created by IntelliJ IDEA.
  User: Daisy
  Date: 2020/5/30
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>GeoPic</title>
    <meta charset="UTF-8">
    <meta name="referrer" content="no-referrer">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <link rel="stylesheet" href="static/css/index.css">
    <script type="text/javascript" src="support/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="static/js/RegisterAndLogin.js"></script>
    <script>
        var register = new RegisterAnfLogin();
    </script>
  </head>

  <body>
  <div class="container">
    <div class="register_box">
      <div class="register_left">
        <p class="register_left_GeoPic">GeoPic</p>
        <p class="register_left_welcomeRegister">欢迎注册</p>
        <p class="register_left_photoManagement">照片智能管理系统</p>
      </div>
      <div class="register_right">
        <div class="register_form" >
          <form method="post" class="register-form" action="##" target="nm_iframe">
            <input type="text"  class="usernameInput" placeholder="请输入用户名/姓名" name="usernameR">
            <input type="password" class="passwordInput" placeholder="请输入密码" name="passwordR">
            <input type="password"  class="passwordInputSure" placeholder="请确认密码" name="passwordSure">
            <button type="submit"  class="btnSubmit" name="submit" onclick="register.register()">提交</button>
          </form>
          <div class="login-text-box">
            <span class="haveAccount-text">已有账号？</span>
            <span class="login-text" onclick="register.loginDirect()">登录</span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="login">
    <div class="login-title">
      <p>登录</p>
    </div>
    <div class="login-box">
      <form class="login-form" ction="##" target="nm_iframe" >
        <div class="username-box">
          <input type="text" class="username" placeholder="请输入用户名/姓名" name="username">
          <img src="static/images/user.png" >
        </div>
        <div class="password-box">
          <input type="password" class="password" placeholder="请输入密码" name="password">
          <img src="static/images/password.png">
        </div>
        <div class="btnSubmitLoginInfo-box">
          <button type="submit" class="btnSubmitLoginInfo"  onclick="register.loginCheck()">开始</button>
        </div>
        <div class="find-password-box" onclick="alert('功能建设中，请稍后尝试......')">
          <span class="forget-password">忘记密码？</span>
          <span class="find-password">找回</span>
        </div>
      </form>
    </div>
  </div>
  </body>
  <iframe id="id_iframe" name="nm_iframe" style="display:none;"></iframe>
</html>
