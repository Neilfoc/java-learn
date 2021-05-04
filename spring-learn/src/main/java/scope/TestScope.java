package scope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestScope {

	public static void main(String[] args) {
		//context就是工厂
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("scope/spring.xml");
		TagDao tagDao = context.getBean("tagDao", TagDao.class);
		//TagDao tagDao1 = context.getBean("tagDao", TagDao.class);
		//System.out.println(tagDao == tagDao1);
		context.destroy();
	}


}
