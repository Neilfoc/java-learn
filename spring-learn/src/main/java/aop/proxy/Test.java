package aop.proxy;

import aop.EmpService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop/spring.xml");
		UserServiceStaticProxy userServiceStaticProxy = context.getBean("userServiceStaticProxy", UserServiceStaticProxy.class);
		//userServiceStaticProxy.save("save");
		//testDynamicProxy();

		//aop
		EmpService empService = context.getBean("empService", EmpService.class);
		System.out.println(empService.getClass());
		empService.save("save");

		//注解aop
		empService.find();
	}

	//测试动态代理 反射
	public static void testDynamicProxy(){
		final UserService userService =  new UserServiceImpl();

		//参数1:当前线程类加载器
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		System.out.println(contextClassLoader);

		//参数2:代理的接口类型数组
		Class[] classes =  new Class[]{UserService.class};

		//参数3:new InvocationHandler()

		//生成这个userServiceProxy 动态代理
		UserService userServiceProxy = (UserService) Proxy.newProxyInstance(contextClassLoader, classes, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				try{
					System.out.println("开启事务");//附加操作
					Object invoke = method.invoke(userService, args);
					System.out.println("提交事务");//附加操作
					return invoke;
				}catch (Exception e){
					System.out.println("回滚事务");//附加操作
				}
				return null;
			}
		});

		userServiceProxy.save("小黑");
	}
}
