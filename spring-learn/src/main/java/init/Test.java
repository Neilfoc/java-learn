package init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author neilfoc
 * @date 2021/4/24 - 17:15
 */
public class Test {

    public static void main(String[] args) {
        //配置文件必须配置在resources目录下，下面的参数值也是基于该目录的相对路径
        ApplicationContext context = new ClassPathXmlApplicationContext("init/spring.xml");

        //参数就是配置文件中填写的id
        UserService userService = context.getBean("userService", UserService.class);

        userService.save("ok");

        //构造方法依赖注入
        ConstructorDI constructorDI = context.getBean("constructorDI1", ConstructorDI.class);
        System.out.println(constructorDI.getName());
        System.out.println(constructorDI.getAge());
        constructorDI.getUserDao().save("构造函数注入");

        //自动注入
        UserServiceImpl userService2 = context.getBean("userService2", UserServiceImpl.class);
        userService2.save("自动注入");
    }
}
