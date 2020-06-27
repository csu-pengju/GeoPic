package semester.cn.persistence.impl;

import semester.cn.domain.UserInfo;
import semester.cn.persistence.UserDao;
import semester.cn.persistence.UtilDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
    private static String findUserByUsenameAndPasswordSQl = "select *" +
            "from users where username= ? and password = ? ";

    private static String registerByUsernameAndPasswordSQL = "insert into users" +
            " (username,password) " +
            " values (?,?)";
     @Override
     public UserInfo findUserByUsernameAndPassword(UserInfo userInfo){
         UserInfo result = null;
         try{
             Connection connection = UtilDao.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(findUserByUsenameAndPasswordSQl);
             preparedStatement.setString(1,userInfo.getUsername());
             preparedStatement.setString(2,userInfo.getPassword());
             ResultSet resultSet = preparedStatement.executeQuery();
             System.out.println(resultSet);
             while (resultSet.next()){
                 result  = new UserInfo();
                 result.setUsername(resultSet.getString("username"));
                 result.setPassword(resultSet.getString("password"));
             }
         }catch (Exception e){
            System.out.println(e.getMessage());
         }
         return result;
     }

     @Override
     public boolean registerByUserAndPassword(UserInfo userInfo){
            boolean resisterResult = false;
            try {
                Connection connection = UtilDao.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(registerByUsernameAndPasswordSQL);
                preparedStatement.setString(1,userInfo.getUsername());
                preparedStatement.setString(2,userInfo.getPassword());
                int num = preparedStatement.executeUpdate();
                if(num>0){
                    resisterResult = true;
                }
                connection.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return resisterResult;
     }
}
