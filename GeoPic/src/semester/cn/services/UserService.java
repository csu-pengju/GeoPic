package semester.cn.services;

import semester.cn.domain.UserInfo;
import semester.cn.persistence.UserDao;
import semester.cn.persistence.impl.UserDaoImpl;

public class UserService {
    private UserDao userDao;
    public UserInfo login(UserInfo userInfo){
        userDao = new UserDaoImpl();
        return  userDao.findUserByUsernameAndPassword(userInfo);
    }
    public boolean register(UserInfo userInfo){
        userDao = new UserDaoImpl();
        return  userDao.registerByUserAndPassword(userInfo);
    }
}
