package aop;

// 原始对象，即目标对象，需要AOP（代理）帮忙增加附加功能
public class EmpServiceImpl implements EmpService {
	@Override
	public void save(String name) {
		System.out.println("save name is:"+name);
	}

	@Override
	public String find() {
		System.out.println("find name is :null");
		return null;
	}
}
