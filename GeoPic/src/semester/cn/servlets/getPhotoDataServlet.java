package semester.cn.servlets;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import org.opencv.core.Core;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

@WebServlet(name = "getPhotoDataServlet")
public class getPhotoDataServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        String line = "";
        String content = "";
        while ((line = reader.readLine()) != null){
            content+=line;
        }

        String jbstring = content.toString();
        String temp[] = jbstring.split(",");
        System.out.println(temp.length);
        String imageName = temp[0];
        String imgStr = temp[1];

        System.out.println("hello"+imageName);

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
            OutputStream o = new FileOutputStream(imagePath);
            o.write(b);
            o.flush();
            o.close();
//            String imageTargetPath = "D:\\Projects\\WebGIS\\GeoPic\\GeoPic\\web\\static\\data\\thumbs\\"+imageName;
//            Mat image = imread(imagePath);
//            Mat target = new Mat();
//            Imgproc.resize(image,target,new Size(100,100),0, 0, Imgproc.INTER_AREA);
//            imwrite(imageTargetPath, target);
//            System.out.write(target.toString().length());

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.write(jbstring);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
