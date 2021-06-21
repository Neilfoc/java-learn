package aop.proxy;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author 11105157
 * @Description
 * @Date 2021/6/21
 */
@Aspect
public class EmpAspect {

    @After()
    public void findAdvice(){
        System.out.println("注解后置通知");
    }
}
