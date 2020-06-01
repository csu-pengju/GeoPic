package semester.cn.servlets;

import semester.cn.domain.UserInfo;
import semester.cn.services.UserService;
import sun.plugin.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//import org.json.JSONObject;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private UserInfo userInfo;
    private UserService userService;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = null;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();

        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        System.out.println(username);
        System.out.println(password);
        userService = new UserService();
        boolean loginResult = userService.register(userInfo);
        if(loginResult){
//            JSONObject object = new JSObject();
            response.getWriter().print("200");
        }else{
            response.getWriter().println("000");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
