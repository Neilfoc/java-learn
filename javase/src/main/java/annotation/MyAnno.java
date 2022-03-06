package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/3/6
 */
@Retention(RetentionPolicy.RUNTIME) //自定义注解想要被解析就必须加上该注解
public @interface MyAnno {

    int a();

    String b();

    Class d();

    int[] f();
}
