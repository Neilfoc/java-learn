package aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BeforeAdvice implements MethodBeforeAdvice {

	//参数1：当前执行方法的Method对象 参数2：当前执行方法的参数 参数3：目标对象
	@Override
	public void before(Method method, Object[] objects, Object o) throws Throwable {
		System.out.println("前置通知："+method.getName());
	}
}
