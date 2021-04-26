package init;

/**
 * @author neilfoc
 * @date 2021/4/24 - 23:22
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(String name) {
        userDao.save(name);
    }
}
