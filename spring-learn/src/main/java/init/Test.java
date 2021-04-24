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
    }
}
