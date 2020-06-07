package semester.cn.servlets;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

@WebServlet(name = "StoreServlet")
@MultipartConfig
//@ MultipartFile
public class StoreServlet extends HttpServlet {
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println(request.getParameter("file"));
        String srcPath = request.getParameter("file");
        FileInputStream fis = new FileInputStream(srcPath);
        FileOutputStream fos = new FileOutputStream("d://eeeee/jpg");
        int len = 0;
        while ((len = fis.read()) != -1) {
            fos.write(len);
        }
        fos.close(); // 后开先关
        fis.close();
//        int totalBytes = request.getContentLength();
//        System.out.println("当前数据总长度:" + totalBytes);
//        InputStream inputStream = request.getInputStream();
//        FileOutputStream fos = new FileOutputStream("D://eee.jpg") ;
//        byte[] bbuf = new byte[totalBytes] ;
//        int hasRead = 0;
//        while((hasRead = inputStream.read(bbuf)) > 0) {
//            fos.write(bbuf, 0, hasRead);//将文件写入服务器的硬盘上  
//        }
//        DataInputStream dataInputStream = new DataInputStream(inputStream);
//        byte[] bytes = new byte[totalBytes];
//        dataInputStream.readFully(bytes);
//
//        fos.close();
//        inputStream.close();

////        out.write();
//        DataInputStream dataInputStream = new DataInputStream(inputStream);
//        byte[] bytes = new byte[totalBytes];
////        dataInputStream.readFully(bytes);
//        int readcount = 0;
//        while(readcount < totalBytes){
//            int aa= dataInputStream.read(bytes,readcount,totalBytes);    //读取输入流，放入bytes数组，返回每次读取的数量
//            readcount = aa + readcount; //下一次的读取开始从readcount开始
//        }
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] buff = new byte[totalBytes];
//        int rc = 0;
//        while ((rc = inputStream.read(buff, 0, totalBytes)) > 0) {
//            byteArrayOutputStream.write(buff, 0, rc);
//        }
//        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("D://ee.jpg"));
//        imageOutput.write(buff, 0, buff.length);
//        imageOutput.close();


//        System.out.println(dataInputStream.toString());

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
