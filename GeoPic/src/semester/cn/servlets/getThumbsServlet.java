package semester.cn.servlets;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import semester.cn.domain.PhotoInfo;
import semester.cn.services.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "getThumbsServlet")
public class getThumbsServlet extends HttpServlet {
    private PhotoInfo photoInfo;
    private PhotoService photoService;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String photoName = request.getParameter("photoName");
        String takenPlace = request.getParameter("takenPlace");
        String takenTime = request.getParameter("takenTime");
        String geo = request.getParameter("geo");
        System.out.println(geo);
        String photoPath = "../data/photos/"+photoName;
        System.out.println(photoPath);
        photoInfo = new PhotoInfo();
        photoInfo.setTakenPlace(takenPlace);
        photoInfo.setTakenTime(toTimeStamp(takenTime));
        photoInfo.setGeo(geo);
        photoInfo.setPhotoPath(photoPath);
        photoService = new PhotoService();
        boolean insertResult = photoService.insertPhotoInfo(photoInfo);
        JSONObject data = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        if(insertResult){
            jsonObject.put("success:","photoInfo insert successfully");
            jsonObject.put("message","200");

        }else{
            jsonObject.put("success:","fail to insert photoInfo");
            jsonObject.put("message","000");
        }
         data.put("data",jsonObject);
        out.write(data.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    private Timestamp toTimeStamp(String time) {

        String[]date = time.split(":");
        String dateString = "";
        if(date.length==5){
            dateString = date[0]+"-"+date[1]+"-"+date[2]+":"+date[3]+":"+date[4];
        }else{
            dateString="00-00-00 00:00:00";
        }
        System.out.println(dateString);
        Timestamp timestamp = Timestamp.valueOf(dateString);
        return timestamp;
    }
}
