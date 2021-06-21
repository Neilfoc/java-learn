package aop.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

/**
 * @author 11105157
 * @Description
 * @Date 2021/6/21
 */
@Aspect
@Service
public class EmpAspect {

    @After(value = "@annotation(aop.aspect.EmpAnnotation)")
    //@After("execution(* aop.EmpServiceImpl.*(..))")
    public void findAdvice(){
        System.out.println("注解后置通知");
    }
}
