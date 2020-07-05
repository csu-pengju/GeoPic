package semester.cn.persistence.impl;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import semester.cn.domain.PhotoInfo;
import semester.cn.persistence.PhotoDao;
import semester.cn.persistence.UtilDao;

import javax.persistence.Id;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class photoDaoIml  implements PhotoDao {
//    private static String insertPhotoInfoSql = "insert into photoinfo " +
//        " (takenplace,takentime,geo,photopath) " +
//        " values (?,?,?,?)";
    private static String getPhotoGPSAndPath = "select st_astext(geo) as geo,photopath " +
        "from photoinfo";
    private static String getAllPhotoPath = "select photopath from photoinfo order by takentime desc";

    @Override
    public PhotoInfo getPhotoByPath(PhotoInfo photoInfo) {

        return null;
    }

    @Override
    public boolean insertPhotoInfoToDB(PhotoInfo photoInfo) {
        boolean insertResult = false;
        Connection connection = null;
        try {
            String insertPhotoInfoSql = "";
            System.out.println(photoInfo.getGeo()+"hellll");
            if(photoInfo.getGeo().equals("no GPSInfo")||photoInfo.getGeo().equals("")){
                System.out.println("我进来了嘛"+photoInfo.getGeo());
               insertPhotoInfoSql = "insert into photoinfo " +
                        " (takenplace,takentime,photopath,photolabels,facesid) " +
                        " values (?,?,?,?,?)";
            }else{
                insertPhotoInfoSql = "insert into photoinfo " +
                        " (takenplace,takentime,geo,photopath,photolabels,facesid) " +
                        " values (?,?,st_geomfromtext('"+photoInfo.getGeo()+"',3857),?,?,?)";
            }

            connection = UtilDao.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(insertPhotoInfoSql);
            preparedStatement.setString(1,photoInfo.getTakenPlace());
            System.out.println(photoInfo.getTakenTime());
            preparedStatement.setTimestamp(2,photoInfo.getTakenTime());
            preparedStatement.setString(3,photoInfo.getPhotoPath());
            preparedStatement.setArray(4, (Array) photoInfo.getPhotoLabels());
            preparedStatement.setArray(5, (Array) photoInfo.getFacesId());
            int num = preparedStatement.executeUpdate();
            if(num>0){
                 insertResult= true;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  insertResult;
    }

    @Override
    public HashMap<String, String> getPhotoGPSAndPath() {
        HashMap <String,String> photoGPSAndPath = new HashMap<String, String>();
        Connection connection = null;
        try {
            connection = UtilDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getPhotoGPSAndPath);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String GPS = resultSet.getString(1);
                String Path = resultSet.getString(2);

                photoGPSAndPath.put(Path, GPS);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  photoGPSAndPath;
    }

    @Override
    public ArrayList<String > getTimeQueryPhotoPath(String startTime, String endTime) {
        Connection conn = null;
        ArrayList<String> timeQueryRes = new ArrayList<>();
        try{
            String getTimeQueryPhotoPathSql = "select photopath from photoinfo " +
                    " where takentime between '"+startTime+"' and '"+endTime+"'";
            conn = UtilDao.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getTimeQueryPhotoPathSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String path = resultSet.getString(1);
                timeQueryRes.add(path);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeQueryRes;
    }

    @Override
    public ArrayList<String> getPlaceQueryPhotoPath(String geo, String address) {
        Connection conn = null;
        ArrayList<String> placeQueryRes = new ArrayList<>();
        HashMap<String,String > temp = new HashMap<>();
        try{
            String getPlaceQueryPhotoPathSql = "select photopath from photoinfo " +
                    "  where ST_Distance(st_transform(st_setsrid(geo,4326),3857), st_transform(st_setsrid('"+
                    geo+"'::geometry,4326),3857))<3000";

            //select st_astext(geo) from photoinfo where ST_Distance(st_transform(st_setsrid(geo,4326),3857),
            //	st_transform(st_setsrid('POINT(112.926388 28.164166)'::geometry,4326),3857)) <10
            conn = UtilDao.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getPlaceQueryPhotoPathSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String path = resultSet.getString(1);
                temp.put(path,path);

            }
            //这里是第二种查询，字符串的模糊匹配
            String getPalceQueryPhotoPathSql2 = "select photopath from photoinfo " +
                    "where takenplace like '%"+address+"%'";
            preparedStatement = conn.prepareStatement(getPalceQueryPhotoPathSql2);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String path = resultSet.getString(1);
                temp.put(path,path);
            }
            for (String key:temp.keySet()) {
                placeQueryRes.add(key);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return placeQueryRes;
    }


    @Override
    public ArrayList<String> getAllPhotoPath() {
        ArrayList<String> res = new ArrayList<>();
        Connection conn;
        try{
            conn = UtilDao.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getAllPhotoPath);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String path = resultSet.getString(1);
                path = path.replace("photos","thumbs");
                res.add(path);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean getThumbs() {
//        System.out.println("Welcome to OpenCV " + Core.VERSION);
//        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
//        System.out.println("OpenCV Mat: " + m);
        return false;
    }

    @Override
    public int getPhotoIdAcoordintPhotoPath(PhotoInfo photoInfo) {
        int photo_id = 0;
        Connection conn = null;
        try{
            System.out.println("photo Pathinget "+photoInfo.getPhotoId());
            String getPhotoIdAccordingPhotoPathSql = "select photo_id from photoinfo " +
                    "where photopath = '"+photoInfo.getPhotoPath()+"'";
            conn = UtilDao.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getPhotoIdAccordingPhotoPathSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                photo_id = resultSet.getInt("photo_id");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photo_id;
    }

    @Override
    public boolean insertPhotoFaceId(PhotoInfo photoInfo) {
        boolean insertPhotoFaceIdRes = false;
        Connection conn= null;
        ArrayList<Integer> arrayList = photoInfo.getFacesId();
        String facesId = "";
        for(int i = 0;i<arrayList.size();i++){
            facesId +=arrayList.get(i);
            if(i<arrayList.size()-1)
            {
                facesId+=",";
            }
        }
        try{
            conn = UtilDao.getConnection();
            String insertPhotoFaceIdSql = "update photoinfo set facesid = '{"+facesId+"}' where photo_id = "+photoInfo.getPhotoId();
            PreparedStatement preparedStatement = conn.prepareStatement(insertPhotoFaceIdSql);
            int num = preparedStatement.executeUpdate();
            System.out.println("人脸id"+num);
            while (num>0){
                insertPhotoFaceIdRes = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertPhotoFaceIdRes;
    }

    @Override
    public boolean insertPhotoLabel(PhotoInfo photoInfo) {
        boolean insertPhotoLabelRes = false;
        Connection conn = null;
        ArrayList<String> arrayList = photoInfo.getPhotoLabels();
        String photoLabels = "";
        for(int i = 0;i<arrayList.size();i++){
            photoLabels +='"'+arrayList.get(i)+'"';
            if(i<arrayList.size()-1)
            {
                photoLabels+=",";
            }
        }

        try{
            conn = UtilDao.getConnection();
            String insertPhotoFaceIdSql = "update photoinfo set  photolabels= '{"+photoLabels+"}' where photopath = '"+photoInfo.getPhotoPath()+"'";
            PreparedStatement preparedStatement = conn.prepareStatement(insertPhotoFaceIdSql);
            int num = preparedStatement.executeUpdate();
            while (num>0){
                insertPhotoLabelRes = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertPhotoLabelRes;
    }

    @Override
    public HashMap<String, String> getPhotoDetail(String photoPath) {
        HashMap<String,String> photoDeatil = new HashMap<String,String>();
        Connection conn = null;
        try{
            String getPhotoDeatilSql = "select takentime,takenplace,facesid,photolabels " +
                    "from photoinfo " +
                    "where photopath ='"+photoPath+"'";
            conn = UtilDao.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getPhotoDeatilSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String takenTime = resultSet.getString("takentime");
                String takenPlace = resultSet.getString("takenplace");
                Array facesId = resultSet.getArray("facesid");
                Array photoLabels = resultSet.getArray("photolabels");
                System.out.println(facesId);
                if(facesId==null){
                    photoDeatil.put("facesId","");
                }else{
                    photoDeatil.put("facesId",facesId.toString());
                }
                if(photoLabels==null){
                    photoDeatil.put("photoLabels","");
                }else{
                    photoDeatil.put("photoLabels",photoLabels.toString());
                }
                photoDeatil.put("takenTime",takenTime);
                photoDeatil.put("takenPlace",takenPlace);

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoDeatil;
    }

    @Override
    public ArrayList<String> getSemanticQueryPhotoPath(PhotoInfo photoInfo) {
        Connection conn = null;
        ArrayList<String> semanticQueryRes = new ArrayList<>();
        try {
            conn = UtilDao.getConnection();
            String getSemanticQueryPhotoPathSql = "select photopath from photoinfo where ";
            String photoLabels = "";
            String facesId = "";

            if(photoInfo.getPhotoLabels()!=null){
                if(photoInfo.getFacesId()!=null){
                    photoLabels = getStringArrayListString(photoInfo.getPhotoLabels());
                    facesId = getIntegerArrayListString(photoInfo.getFacesId());
                    getSemanticQueryPhotoPathSql+=" photolabels @> '{"+photoLabels+"}' and facesid @> '{"+facesId+"}'";
                }else{

                    photoLabels = getStringArrayListString(photoInfo.getPhotoLabels());
                    getSemanticQueryPhotoPathSql+=" photolabels @> '{"+photoLabels+"}'";
                }
            }else{
                if(photoInfo.getFacesId()!=null){

                    facesId = getIntegerArrayListString(photoInfo.getFacesId());
                    getSemanticQueryPhotoPathSql += " facesid @>'{"+facesId+"}'";
                }else{
                    return null;
                }
            }

            PreparedStatement preparedStatement = conn.prepareStatement(getSemanticQueryPhotoPathSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                semanticQueryRes.add(resultSet.getString("photopath"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  semanticQueryRes;
    }

    @Override
    public ArrayList<String> getIntegratedQueryPhotoPath(PhotoInfo photoInfo,String startTime,String endTime,String geo,String address) {
        Connection conn = null;
        ArrayList<String >integratedQueryRes = new ArrayList<>();
        try{
            String getIntegratedQueryPhotoPathSql = "select photopath from photoinfo where ";
            ArrayList<String> timeQueryRes = getTimeQueryPhotoPath(startTime,endTime);

            ArrayList<String > semanticQueryRes = getSemanticQueryPhotoPath(photoInfo);

            if(startTime!=""){
                integratedQueryRes = timeQueryRes;
//                System.out.println("你在哪里1");
                if(geo!=""){
//                    System.out.println("你在哪里2");
                    ArrayList<String > placeQueryRes = getPlaceQueryPhotoPath(geo,address);
                    integratedQueryRes = getSames(integratedQueryRes,placeQueryRes);
                    if(photoInfo.getPhotoLabels()!=null||photoInfo.getFacesId()!=null){
//                        System.out.println("你在哪里3");
                        integratedQueryRes = getSames(integratedQueryRes,semanticQueryRes);
                    }
                }else{
//                    System.out.println("你在哪里4");
                    if(photoInfo.getPhotoLabels()!=null||photoInfo.getFacesId()!=null){
//                        System.out.println("你在哪里5");
                        System.out.println(integratedQueryRes);
                        System.out.println(semanticQueryRes);
                        integratedQueryRes = getSames(integratedQueryRes,semanticQueryRes);
                    }
                }
            }else{
                if(geo!=""){
//                    System.out.println("你在哪里6");
                    ArrayList<String > placeQueryRes = getPlaceQueryPhotoPath(geo,address);
                    integratedQueryRes = placeQueryRes;
                    if(photoInfo.getPhotoLabels()!=null||photoInfo.getFacesId()!=null){
                        integratedQueryRes = getSames(integratedQueryRes,semanticQueryRes);
                    }
                }else{
//                    System.out.println("你在哪里7");
                    if(photoInfo.getPhotoLabels()!=null||photoInfo.getFacesId()!=null){
                        integratedQueryRes = semanticQueryRes;
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return integratedQueryRes;
    }

    @Override
    public HashMap<String, String> getAllTimeLinePhotoPath() {
        HashMap<String,String> res = new HashMap<>();
        Connection conn = null;
        try{
            conn = UtilDao.getConnection();
            String getAllTimeLinePhotoPathSql = "select photopath,takentime " +
                    "from photoinfo " +
                    "where takentime !='9999-01-01 00:00:00'  " +
                    "order by takentime desc ";
            PreparedStatement preparedStatement = conn.prepareStatement(getAllTimeLinePhotoPathSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String time = resultSet.getTimestamp("takentime").toString();
                String path = resultSet.getString("photopath");
                path = path.replace("photos","thumbs");
                res.put(path,time);

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private String getStringArrayListString(ArrayList<String> arrayList){
        String listString = "";
        for(int i = 0;i<arrayList.size();i++){
            listString +='"'+arrayList.get(i)+'"';
            if(i<arrayList.size()-1)
            {
                listString+=",";
            }
        }
        return listString;
    }

    private String getIntegerArrayListString(ArrayList<Integer> arrayList){
        String listString = "";
        for(int i = 0;i<arrayList.size();i++){
            listString +=arrayList.get(i);
            if(i<arrayList.size()-1)
            {
                listString+=",";
            }
        }
        return listString;
    }


    private  ArrayList<String> getSames(ArrayList<String> arr1,ArrayList<String> arr2){
        ArrayList<String> sames = new ArrayList<>();
        if(arr1!=null&&arr2!=null){
            for(int i = 0;i<arr1.size();i++){
                for(int j = 0;j< arr2.size();j++){
                    if(arr1.get(i).equals(arr2.get(j)))
                    {
                        sames.add(arr1.get(i));
                    }
                }
            }
        }
        return sames;

    }


}

