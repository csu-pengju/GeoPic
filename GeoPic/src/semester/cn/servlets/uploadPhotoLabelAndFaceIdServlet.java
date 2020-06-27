package semester.cn.servlets;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.util.ArrayList;

@WebServlet(name = "uploadPhotoLabelAndFaceIdServlet")
public class uploadPhotoLabelAndFaceIdServlet extends HttpServlet {
    PhotoService photoService;
    PhotoInfo photoInfo;
    FaceService faceService;
    FaceInfo faceInfo;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");

        JSONObject resObj = new JSONObject();
        switch (type){
            case"photoLabel":
                boolean updatePhotoLabelres = updatePhotoLabel(request);
                if(updatePhotoLabelres){
                    resObj.put("message","update PhotoLabel successfully");
                    resObj.put("success",true);

                }else{
                    resObj.put("message","fail to update PhotoLabel");
                    resObj.put("success",false);
                }
                break;
            case "faceId":
                boolean res = updatePhotoFaceId(request);
                if(res){
                    resObj.put("message","update FaceId successfully");
                    resObj.put("success",true);
                }else {
                    resObj.put("message","fail to update FaceId ");
                    resObj.put("success",false);
                }

                break;
            default:
                break;
        }
        out.write(resObj.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected boolean updatePhotoLabel(HttpServletRequest request){
        boolean updateRes = false;
        String photoLabels = request.getParameter("photoLabels");
        String photoPath = request.getParameter("photoPath");
        ArrayList<String>photolabels = new ArrayList<>();
        if(photoLabels!=""){
            photolabels = getFormattedPhotoLabels(photoLabels);
            photoInfo = new PhotoInfo();
            photoInfo.setPhotoLabels(photolabels);
            photoInfo.setPhotoPath(photoPath);
            photoService = new PhotoService();
            updateRes = photoService.insertPhotoLabel(photoInfo);

        }else{
            updateRes =false;
        }

        return  updateRes;
    }

    protected ArrayList<String>getFormattedPhotoLabels(String photoLabels){
        ArrayList<String>photolabels = new ArrayList<>();
        System.out.println("我在getformattd"+photoLabels);
        if(photoLabels.contains(",")){
            String []photoLabel = photoLabels.split(",");
            for(int i = 0;i<photoLabel.length;i++){
                photolabels.add(photoLabel[i]);
            }
        }else {
            photolabels.add(photoLabels);
        }
        return photolabels;
    }

    protected boolean updatePhotoFaceId(HttpServletRequest request){
        boolean updateRes = false;
        String photoPath = request.getParameter("photoPath");
        String facesPath = request.getParameter("facesPath");
        String faceTokens = request.getParameter("faceTokens");
        ArrayList<Integer> facesId = new ArrayList<>();
        faceInfo = new FaceInfo();
        if(facesPath.contains(",")){
            String []facePath = facesPath.split(",");
            for(int i = 0;i<facePath.length;i++){
                faceInfo.setFacePath(facePath[i]);
                faceService = new FaceService();
                int faceId = faceService.getFaceIdAccordingFacePath(faceInfo);
                facesId.add(faceId);
            }
        }else if(facesPath!=""){

            faceInfo.setFacePath(facesPath);
            faceService = new FaceService();
            int faceId = faceService.getFaceIdAccordingFacePath(faceInfo);
            facesId.add(faceId);
        }else{
            updateRes = false;
        }

        if(faceTokens.contains(",")){
            String[] faceToken = faceTokens.split(",");
            for(int i = 0;i<faceToken.length;i++){

                ArrayList<String> faceTokenList = new ArrayList<>();
                faceTokenList.add(faceToken[i]);
                faceInfo.setFaceTokens(faceTokenList);
                faceService = new FaceService();
                int faceId = faceService.getFaceIdAccordingFaceToken(faceInfo);
                facesId.add(faceId);
            }
        }else if(faceTokens!=""){

            ArrayList<String> faceTokenList = new ArrayList<>();
            faceTokenList.add(faceTokens);
            faceInfo.setFaceTokens(faceTokenList);
            faceService = new FaceService();
            int faceId = faceService.getFaceIdAccordingFaceToken(faceInfo);
            facesId.add(faceId);
        }else {
            updateRes = false;
        }
        photoInfo = new PhotoInfo();

        photoInfo.setPhotoPath(photoPath);
        photoService = new PhotoService();
        photoInfo.setPhotoId(photoService.getPhotoIdAccordingPhotoPath(photoInfo));
        photoInfo.setFacesId(facesId);

        updateRes = photoService.insertPhotoFacesId(photoInfo);

        System.out.println(photoPath);
        System.out.println("JHHHJ"+facesPath);
        return  updateRes;
    }
}
