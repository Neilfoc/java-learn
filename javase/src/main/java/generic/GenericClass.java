package generic;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/12/26
 */
public class GenericClass<T> {
    public String test(T t){
        return t.getClass().toString();
    }
}
