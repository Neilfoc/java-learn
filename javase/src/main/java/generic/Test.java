package generic;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/12/26
 */
public class Test {
    public static void main(String[] args) {
        JieKouImpl1 jieKouImpl1 = new JieKouImpl1();
        JieKouImpl2 jieKouImpl2 = new JieKouImpl2();
        GenericClass<Jiekou> gc = new GenericClass<>();
        System.out.println(gc.test(jieKouImpl1));
        System.out.println(gc.test(jieKouImpl2));
    }
}
