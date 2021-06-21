package aop.aspect;

import java.lang.annotation.*;

/**
 * @author 11105157
 * @Description
 * @Date 2021/6/21
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmpAnnotation {
    String desc() default "";
}
