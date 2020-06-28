package semester.cn.servlets;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.opencv.photo.Photo;
import semester.cn.domain.FaceInfo;
import semester.cn.domain.PhotoInfo;
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
import java.util.ArrayList;

@WebServlet(name = "getQueryPhotoPathServlet")
public class getQueryPhotoPathServlet extends HttpServlet {
    private PhotoService photoService;
    private PhotoInfo photoInfo;
    private FaceInfo faceInfo;
    private FaceService faceService;

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
        ArrayList<String> res = new ArrayList<>();
        switch (condition){
            case("timeQuery"):
                photoService = new PhotoService();
                res = photoService.getTimeQueryPhotoPath(startTime,endTime);

                break;
            case("placeQuery"):
                photoService = new PhotoService();
                res = photoService.getPlaceQueryPhotoPath(geo,address);

                break;
            case("semanticQuery"):
                photoService = new PhotoService();
                photoInfo = setSemanticQueryParams(photoLabels,faceLabels);
                res = photoService.getSemanticQueryPhotoPath(photoInfo);
                break;
            case("integratedQuery"):
                photoService = new PhotoService();
                photoInfo = setSemanticQueryParams(photoLabels,faceLabels);
                res = photoService.getIntegratedQueryPhotoPath(photoInfo,startTime,endTime,geo,address);
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

    /**
     *  getArrayList from a str
     * @param str String to transform to ArrayList
     * @return ArrayList
     */
    protected ArrayList<String> getArrayListFromString(String str){
        ArrayList<String> res = new ArrayList<>();
        System.out.println(res.size());
        if(str.contains("/")){
            String []strs = str.split("/");
            for(int i = 0;i<strs.length;i++){
                res.add(strs[i]);
            }
        }else{
            res.add(str);
        }
        System.out.println(res);
        return res;
    }

    /**
     *
     * @param faceLabels faceLabel get from 前端
     * @return 每个faceLabel对应的faceId
     */
    protected ArrayList<Integer> getFaceIdArrayList(String faceLabels){
        ArrayList<Integer> res = new ArrayList<>();
        ArrayList <String>faceLabelsArrList = new ArrayList<>();
        faceLabelsArrList = getArrayListFromString(faceLabels);
        faceService = new FaceService();
        if(faceLabelsArrList.size()>0){
            for(int i = 0;i<faceLabelsArrList.size();i++){
                faceInfo = new FaceInfo();
                faceInfo.setFaceLabel( faceLabelsArrList.get(i));
                int faceId = faceService.getFaceIdAccordingFaceLabel(faceInfo);
                if(faceId!=-1){
                    res.add(faceId);
                }
            }
        }
        return res;
    }

    protected PhotoInfo setSemanticQueryParams(String photoLabels,String faceLabels){
        photoInfo = new PhotoInfo();
        if(photoLabels!=""){
            photoInfo.setPhotoLabels(getArrayListFromString(photoLabels));
        }
        if(faceLabels!=""){
            photoInfo.setFacesId(getFaceIdArrayList(faceLabels));
        }
        return photoInfo;
    }



}
