package tests.copyTest;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/4
 */
public class GetSetPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        Account s = (Account) source;
        Account t = (Account) target;
        t.setAge(s.getAge());
        t.setMoney(s.getMoney());
        t.setName(s.getName());
    }
}
