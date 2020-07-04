package semester.cn.servlets;

import com.sun.org.apache.xerces.internal.xs.StringList;
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
import java.util.*;

@WebServlet(name = "getTimeLinePhotoPathServlet")
public class getTimeLinePhotoPathServlet extends HttpServlet {
    private PhotoService photoService;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        photoService = new PhotoService();
        JSONObject data = new JSONObject();
        HashMap<String ,String > res = photoService.getAllTimeLinePhotoPath();

        data = getFormatTimeLinePhoto(res);
        out.write(data.toString());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected JSONObject getFormatTimeLinePhoto(HashMap<String ,String> data){
        JSONObject resObj = new JSONObject();
        JSONObject timeLine = new JSONObject();
        JSONArray  resArray = new JSONArray();
        String lastVal = "";
        String currentVal = "";
        String currentKey = "";
        String lastKey = "";
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(data.entrySet());

        //这里需要根据value进行排序
        list.sort((Comparator<? super HashMap.Entry<String, String>>) new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for(HashMap.Entry<String , String> entry:list) {
            currentVal = entry.getValue();
            currentKey = entry.getKey();

            String tem[] = currentVal.split("-");
            if(tem.length==3){
                currentVal = tem[0]+tem[1];
            }

            //注意java中字符串比较大小不能直接用==，比较相等可以用equal,比较大小可以用compareTo
            if((!currentVal.equals(lastVal))&&lastVal!=""){
                timeLine.put(lastVal,resArray);
                resArray.clear();
                resArray.add(currentKey);
            }else{
                resArray.add(currentKey);
            }
            lastKey = currentKey;
            lastVal = currentVal;
        }
        timeLine.put(lastVal,resArray);
        resObj.put("data",timeLine);
        if(timeLine.size()>0){
            resObj.put("success","200");
            resObj.put("message","getTimePhotoPath successfully");
        }else{
            resObj.put("success","000");
            resObj.put("message","fail to get TimePhotoPath.");
        }
        return resObj;

    }
}
