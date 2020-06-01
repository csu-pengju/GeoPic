package semester.cn.persistence;

import semester.cn.domain.UserInfo;

public interface UserDao {
    public UserInfo findUserByUsernameAndPassword(UserInfo userInfo);
    public boolean registerByUserAndPassword(UserInfo userInfo);
}
