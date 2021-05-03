package mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import mybatis.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mybatis/spring.xml");
		/*SqlSessionFactory sqlSessionFactory = context.getBean("sqlSessionFactory", SqlSessionFactory.class);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		System.out.println(sqlSession);
		SqlSessionFactory sqlSessionFactory2 = context.getBean("sqlSessionFactory2", SqlSessionFactory.class);
		SqlSession sqlSession2 = sqlSessionFactory2.openSession();
		System.out.println(sqlSession2);
		UserDao userDao = context.getBean("userDao", UserDao.class);
		List<User> users = userDao.findAll();
		for (User user : users) {
			System.out.println(user);
		}*/
		UserService userService = context.getBean("userService", UserService.class);
		User user = new User();
		user.setId(UUID.randomUUID().toString().replaceAll("-",""));
		user.setName("neil");
		user.setAge(18);
		user.setBir(new Date());
		userService.save(user);
	}
}
