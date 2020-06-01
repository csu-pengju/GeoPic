package semester.cn.servlets;

import semester.cn.domain.UserInfo;
import semester.cn.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    private UserInfo userInfo;
    private UserService userService;
    private static String photoWall= "/static/html/photoWall.html";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = null;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);

        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userService = new UserService();
        UserInfo loginResult = userService.login(userInfo);
        System.out.println(loginResult);
        if(loginResult==null){
            response.getWriter().print("用户名或密码不正确");
        }else{
            response.getWriter().print("200");
//            response.sendRedirect(photoWall);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
