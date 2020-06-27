package semester.cn.servlets;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import semester.cn.services.FaceService;
import semester.cn.services.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

@WebServlet(name = "getPhotoDetailServlet")
public class getPhotoDetailServlet extends HttpServlet {
    private PhotoService photoService;
    private FaceService faceService;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String photoPath = request.getParameter("photoPath");
        HashMap<String,String> photoDetail = new HashMap<String,String>();
        JSONObject jsonObject = new JSONObject();
        JSONArray facesIdArray = new JSONArray();
        photoService = new PhotoService();
        photoDetail = photoService.getPhotoDetail(photoPath);
        if(photoDetail.size()>0){
            String takenTime = photoDetail.get("takenTime");
            String takenPlace = photoDetail.get("takenPlace");
            String facesid = photoDetail.get("facesId");
            String photoLabels = photoDetail.get("photoLabels");
            facesIdArray = getFacePathFromFaceId(facesid);
            jsonObject.put("message","get PhotoDetail successfully");
            jsonObject.put("success",true);
            jsonObject.put("takenTime",takenTime);
            jsonObject.put("takenPlace",takenPlace);
            jsonObject.put("facesId",facesIdArray);
            jsonObject.put("photoLabels",handlePhotoLabels(photoLabels));
        }else{
            jsonObject.put("message","fail to get PhotoDetail");
            jsonObject.put("success",false);
        }

        out.write(jsonObject.toString());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected JSONArray getFacePathFromFaceId(String facesid){
        JSONArray facesIdArray = new JSONArray();

        if(facesid.indexOf(",")>-1){
            String []faces = facesid.split("\\{")[1].split(",");
            for(int i = 0;i<faces.length;i++){
                if(i<faces.length-1){
                    int faceId = Integer.parseInt(faces[i]);
                    JSONObject jsonObject = new JSONObject();
                    HashMap<String ,String > facePathAndLabel = getFacePathAndLabel(faceId);
                    for(String key:facePathAndLabel.keySet()){
                        jsonObject.put("facePath",key);
                        jsonObject.put("faceLabel",facePathAndLabel.get(key));
                    }
                    facesIdArray.add(jsonObject);
                }else{
                    int faceId = Integer.parseInt(faces[i].split("\\}")[0]);
                    JSONObject jsonObject = new JSONObject();
                    HashMap<String ,String > facePathAndLabel = getFacePathAndLabel(faceId);
                    for(String key:facePathAndLabel.keySet()){
                        jsonObject.put("facePath",key);
                        jsonObject.put("faceLabel",facePathAndLabel.get(key));
                    }
                    facesIdArray.add(jsonObject);
                }
            }
        }else if(facesid!=""){
            int faceId = Integer.parseInt(facesid.split("\\{")[1].split("\\}")[0]);
            JSONObject jsonObject = new JSONObject();
            HashMap<String ,String > facePathAndLabel = getFacePathAndLabel(faceId);
            for(String key:facePathAndLabel.keySet()){
                jsonObject.put("facePath",key);
                jsonObject.put("faceLabel",facePathAndLabel.get(key));
            }
            facesIdArray.add(jsonObject);
        }
        return facesIdArray;
    }

    //根据faceid获取facePath
    protected HashMap<String ,String> getFacePathAndLabel(int faceId){
        HashMap<String,String >facePathAndLabel = new HashMap<>();
        faceService = new FaceService();
        facePathAndLabel = faceService.getFacePathAndLabel(faceId);
        return facePathAndLabel;
    }

    //处理从数据库中拿到的photoLabels格式
    protected String handlePhotoLabels(String photoLabels){
        String photoLabelsFormat = "";
        if(photoLabels!=""){
            photoLabelsFormat = photoLabels.split("\\{")[1].split("\\}")[0];
        }
        return photoLabelsFormat;
    }

}
