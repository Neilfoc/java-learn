package init;

/**
 * @author neilfoc
 * @date 2021/4/24 - 16:50
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void save(String name) {
        System.out.println("name = " + name);
    }
}
