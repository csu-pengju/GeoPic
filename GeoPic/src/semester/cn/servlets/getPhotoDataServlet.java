package semester.cn.servlets;

import net.sf.json.JSONObject;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import semester.cn.domain.PhotoInfo;
import semester.cn.services.PhotoService;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
//import com.facepp.http.PostParameters;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

@WebServlet(name = "getPhotoDataServlet")
public class getPhotoDataServlet extends HttpServlet {
    PhotoService photoService;
    PhotoInfo photoInfo;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        JSONObject res = new JSONObject();
        String line = "";
        String content = "";
        while ((line = reader.readLine()) != null){
            content+=line;
        }
        String jbstring = content.toString();

        String temp[] = jbstring.split(",");

        String imageName = temp[0];
        String imgStr = temp[1];
        String path = "D:\\Projects\\WebGIS\\GeoPic\\GeoPic\\web\\static\\data\\photos\\";
        boolean judgeIsExistRes = judgeIsExist(imageName,path);
        if(judgeIsExistRes){
            res.put("message","图片已存在，不重复入库");
            res.put("success",false);
            out.write(res.toString());
            return;
        }else{
            BASE64Decoder decoder = new BASE64Decoder();
            try{
                //Base64解码
                byte[] b = decoder.decodeBuffer(imgStr);
                for(int i = 0;i<b.length;i++){
                    if(b[i]<0){
                        b[i]+=256;
                    }
                }
                String imagePath = "D:\\Projects\\WebGIS\\GeoPic\\GeoPic\\web\\static\\data\\photos\\"+imageName;
                String thums = "D:\\Projects\\WebGIS\\GeoPic\\GeoPic\\web\\static\\data\\faces\\"+imageName;
                System.out.println(imagePath);
                OutputStream o = new FileOutputStream(imagePath);
                o.write(b);
                o.flush();
                o.close();
                res.put("message","可以入库");
                res.put("success",true);
                out.write(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected boolean judgeIsExist(String photoName,String photoDir){
        boolean isExist = false;
        File file = new File(photoDir);
        File []array = file.listFiles();
        for(int i = 0;i<array.length;i++){
            if(array[i].isFile()){
                String imgName = array[i].getName();
                if(imgName.equals(photoName)){
                    isExist = true;
                    break;
                }
            }
        }
        return isExist;
    }


}
