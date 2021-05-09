package mybatis.aop;

import mybatis.User;
import mybatis.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.UUID;

/**
 * @author neilfoc
 * @date 2021/5/4 - 0:04
 */
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mybatis/spring.xml");
        UserService userServiceImplAOP = context.getBean("userServiceImplAOP", UserService.class);
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setName("小强");
        user.setAge(20);
        user.setBir(new Date());
        userServiceImplAOP.save(user);
        //System.out.println(userServiceImplAOP);
    }
}
