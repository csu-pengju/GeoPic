package semester.cn.servlets;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "getPhotoGPSServlet")
public class getPhotoGPSServlet extends HttpServlet {
    private PhotoService photoService;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        photoService = new PhotoService();
        JSONObject res = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        HashMap<String,String> photoGPSAndPath = new HashMap<String, String>();
        photoGPSAndPath = photoService.getPhotoGPSAndPath();
        if(photoGPSAndPath.isEmpty()){
            res.put("message","fail to get PhotoGSPAndPath");
        }else{
            res.put("message","get PhotoGSPAndPath successfully");
        }
        Iterator iterator = photoGPSAndPath.entrySet().iterator();
        JSONArray jsonArray = new JSONArray();
        int i = 0;
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String Path = (String)entry.getKey();
            String GPS = (String)entry.getValue();
            System.out.println(GPS+"ddd");
            if(GPS!=null){
                jsonObject.put("Path",Path);
                jsonObject.put("GPS",GPS);
                jsonArray.add(i++,jsonObject);
            }
        }
        data.put("GPSAndPath",jsonArray);
        res.put("data",data);
        System.out.println(res.toString());
        out.write(data.toString());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
