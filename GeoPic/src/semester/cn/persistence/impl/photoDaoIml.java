package semester.cn.persistence.impl;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.xerces.internal.xs.StringList;
import semester.cn.domain.PhotoInfo;
import semester.cn.persistence.PhotoDao;
import semester.cn.persistence.UtilDao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class photoDaoIml  implements PhotoDao {
//    private static String insertPhotoInfoSql = "insert into photoinfo " +
//        " (takenplace,takentime,geo,photopath) " +
//        " values (?,?,?,?)";
    private static String getPhotoGPSAndPath = "select st_astext(geo) as geo,photopath " +
        "from photoinfo";

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
            System.out.println(photoInfo.getGeo());
            if(photoInfo.getGeo().equals("no GPSInfo")){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  photoGPSAndPath;
    }


}
