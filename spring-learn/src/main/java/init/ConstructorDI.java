package init;


/**
 * @author neilfoc
 * @date 2021/4/26 - 0:08
 */

public class ConstructorDI {

    private String name;

    private Integer age;

    private UserDao userDao;

    public ConstructorDI(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public ConstructorDI(String name, Integer age, UserDao userDao) {
        this.name = name;
        this.age = age;
        this.userDao = userDao;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
