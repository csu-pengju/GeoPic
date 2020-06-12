package semester.cn.servlets;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import semester.cn.services.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "getQueryPhotoPathServlet")
public class getQueryPhotoPathServlet extends HttpServlet {
    private PhotoService photoService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String condition = request.getParameter("condition");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String address = request.getParameter("address");
        String geo = request.getParameter("geo");
        String photoLabels = request.getParameter("photoLabels");
        String faceLabels = request.getParameter("faceLabels");
        System.out.println("condition ："+condition);
        System.out.println("address"+address);
        System.out.println("geo"+geo);
        ArrayList<String> res = new ArrayList<>();
        switch (condition){
            case("timeQuery"):
                photoService = new PhotoService();
                res = photoService.getTimeQueryPhotoPath(startTime,endTime);
                System.out.println("时间查询"+res);
                break;
            case("placeQuery"):
                photoService = new PhotoService();
                res = photoService.getPlaceQueryPhotoPath(geo,address);
                System.out.println("地点查询"+res);
                break;
            case("semanticQuery"):
                break;
            case("integratedQuery"):
                break;
            default:
                break;
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(res.size()>0){
            jsonObject.put("message","query success");
            jsonObject.put("success",200);
            for(String path:res){
                jsonArray.add(path);
            }
        }else{
            jsonObject.put("message","fail to getQuery result");
            jsonObject.put("success",000);
        }
        jsonObject.put("photoPath",jsonArray);
        out.write(jsonObject.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
