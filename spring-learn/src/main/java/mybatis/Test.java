package mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mybatis/spring.xml");
		SqlSessionFactory sqlSessionFactory = context.getBean("sqlSessionFactory", SqlSessionFactory.class);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		System.out.println(sqlSession);
		SqlSessionFactory sqlSessionFactory2 = context.getBean("sqlSessionFactory2", SqlSessionFactory.class);
		SqlSession sqlSession2 = sqlSessionFactory2.openSession();
		System.out.println(sqlSession2);
	}
}
