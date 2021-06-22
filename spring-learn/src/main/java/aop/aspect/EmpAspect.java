package aop.aspect;

import aop.Emp;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Map;

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
    public void findAdvice(JoinPoint jp){
        System.out.println("注解后置通知");
        Object[] args = jp.getArgs();
       /* Emp arg = (Emp) args[0];
        System.out.println(arg.getId());*/
        Map map = JSON.parseObject(JSON.toJSONString(args[0]), Map.class);
        System.out.println("arg0.id is "+map.get("id"));
        System.out.println(JSON.toJSONString(args));
        MethodSignature signature = (MethodSignature) jp.getSignature();
        EmpAnnotation annotation = signature.getMethod().getAnnotation(EmpAnnotation.class);
        System.out.println(annotation.desc());
    }
}
