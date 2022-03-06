package annotation;

import java.util.Arrays;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/3/6
 */
@MyAnno(a = 10,
        b = "hello",
        d = String.class,
        f = {100,200})
public class Demo {

    public static void main(String[] args) {
        // 解析注解 使用反射
        Class<Demo> clazz = Demo.class;
        if (clazz.isAnnotationPresent(MyAnno.class)) {
            MyAnno annotation = clazz.getAnnotation(MyAnno.class);
            System.out.println(annotation.a());
            System.out.println(annotation.b());
            System.out.println(annotation.d().getName());
            System.out.println(Arrays.toString(annotation.f()));
        }
    }
}
