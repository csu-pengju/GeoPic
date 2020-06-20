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
        System.out.println(type);
        switch (type){
            case"photoLabel":
                updatePhotoLabel(request);
                break;
            case "faceId":
                boolean res = updatePhotoFaceId(request);
                System.out.println("人脸id成功了吗"+res);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected boolean updatePhotoLabel(HttpServletRequest request){

        return  false;
    }

    protected boolean updatePhotoFaceId(HttpServletRequest request){
        boolean updateRes = false;
        String photoPath = request.getParameter("photoPath");
        String facesPath = request.getParameter("facesPath");
        ArrayList<Integer> facesId = new ArrayList<>();
        if(facesPath.contains(",")){
            String []facePath = facesPath.split(",");
            for(int i = 0;i<facePath.length;i++){
                faceInfo = new FaceInfo();
                faceInfo.setFacePath(facePath[i]);
                faceService = new FaceService();
                int faceId = faceService.getFaceIdAccordingFacePath(faceInfo);
                facesId.add(faceId);
            }
        }else if(facesPath!=""){
            faceInfo = new FaceInfo();
            faceInfo.setFacePath(facesPath);
            faceService = new FaceService();
            int faceId = faceService.getFaceIdAccordingFacePath(faceInfo);
            facesId.add(faceId);
        }else{
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
