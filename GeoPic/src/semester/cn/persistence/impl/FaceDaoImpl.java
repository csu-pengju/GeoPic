package semester.cn.persistence.impl;

import semester.cn.domain.FaceInfo;
import semester.cn.persistence.FaceDao;
import semester.cn.persistence.UtilDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

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
            String insertFaceInfoSql = "";{

            }
            insertFaceInfoSql = "insert into faceinfo " +
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateFaceLabelResult;
    }
}
