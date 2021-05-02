package complexbean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;

public class Test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("complexbean/spring.xml");
		Calendar calendar = context.getBean("calendar", Calendar.class);
		System.out.println(calendar.getTime());
	}


}
