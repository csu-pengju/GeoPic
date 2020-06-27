package semester.cn.persistence.impl;

import semester.cn.domain.FaceInfo;
import semester.cn.persistence.FaceDao;
import semester.cn.persistence.UtilDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class FaceDaoImpl implements FaceDao {

    @Override
    public boolean insertFaceInfoToDB(FaceInfo faceInfo) {
        boolean insertResult = false;
        Connection connection = null;
        try {
            ArrayList<String> arrayList = faceInfo.getFaceTokens();
            String facetokens = "";
            for(int i = 0;i<arrayList.size();i++){
                facetokens +=arrayList.get(i);
                if(i<arrayList.size()-1)
                {
                    facetokens+=",";
                }

            }
            String insertFaceInfoSql = "insert into faceinfo " +
                    " (facetoken,facepath) " +
                    " values ('{"+facetokens+"}',?)";
            connection = UtilDao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertFaceInfoSql);
//            preparedStatement.setArray(1,(Array)faceInfo.getFaceTokens());
            preparedStatement.setString(1,faceInfo.getFacePath());
            int num = preparedStatement.executeUpdate();
            if(num>0){
                insertResult = true;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertResult;
    }

    @Override
    public boolean updateFaceLabelToDB(FaceInfo faceInfo) {
        boolean updateFaceLabelResult = false;
        Connection conn = null;
        try{
            System.out.println(faceInfo.getFacePath());
            String updateFaceLabelSql = "update faceinfo set facelabel = '" +
                      faceInfo.getFaceLabel()+"'"+
                    " where facepath ='"+faceInfo.getFacePath()+"'";
            conn = UtilDao.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateFaceLabelSql);
            int result = preparedStatement.executeUpdate();
            System.out.println(result);
            if(result>0){
                updateFaceLabelResult = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateFaceLabelResult;
    }

    @Override
    public int getFaceIdAccordingFacePath(FaceInfo faceInfo) {
        int id = 0;
        Connection conn;
        try{
            conn = UtilDao.getConnection();
            String getFaceIdSql = "select face_id from faceinfo where facepath = '"+faceInfo.getFacePath()+"'limit 1";
            PreparedStatement preparedStatement = conn.prepareStatement(getFaceIdSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int getFaceIdAccordingFaceToken(FaceInfo faceInfo) {
        int id = 0;
        Connection conn;
        try{
            conn = UtilDao.getConnection();
            ArrayList<String> faceTokensArr = faceInfo.getFaceTokens();
            String faceTokens = "";
            for(int i = 0;i<faceTokensArr.size();i++){
                faceTokens +=faceTokensArr.get(i);
                if(i<faceTokensArr.size()-1)
                {
                    faceTokens+=",";
                }

            }
            System.out.println(faceTokens);
            String getFaceIdSql = "select face_id from faceinfo where facetoken = '{"+faceTokens+"}'limit 1";
            PreparedStatement preparedStatement = conn.prepareStatement(getFaceIdSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public HashMap<String,String > getFacePathAndLabel(int faceId) {
        String facePath = "";
        String faceLabel = "";
        HashMap<String,String>facePathAndLabel = new HashMap<>();
        Connection conn;
        try{
            String getFacePathSql = "select facepath,facelabel from faceinfo " +
                    "where face_id = "+faceId +" limit 1";
            conn = UtilDao.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getFacePathSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                facePath = resultSet.getString("facepath");
                faceLabel = resultSet.getString("facelabel");
                facePathAndLabel.put(facePath,faceLabel);

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facePathAndLabel;
    }
}
