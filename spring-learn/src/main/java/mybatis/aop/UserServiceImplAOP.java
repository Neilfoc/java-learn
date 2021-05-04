package mybatis.aop;

import mybatis.User;
import mybatis.UserDao;
import mybatis.service.UserService;

/**
 * @author neilfoc
 * @date 2021/5/3 - 22:21
 */
public class UserServiceImplAOP implements UserService {
    private UserDao userDao;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void save(User user) {
        userDao.save(user);
        //抛出异常
        int i = 1 / 0;
    }
}
