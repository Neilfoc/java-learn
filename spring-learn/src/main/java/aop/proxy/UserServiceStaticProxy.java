package aop.proxy;

//静态代理类
//开发原则:代理类和目标类实现相同接口,依赖于真正的目标类
public class UserServiceStaticProxy implements UserService {

	//真正的目标类 //target 原始业务逻辑对象
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void save(String name) {
		try {
			System.out.println("开启事务");
			userService.save(name);//调用真正业务逻辑方法
			System.out.println("提交事务");
		}catch (Exception e){
			System.out.println("回滚事务");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String id) {
		try {
			System.out.println("开启事务");
			userService.delete(id);//调用真正业务逻辑方法
			System.out.println("提交事务");
		}catch (Exception e){
			System.out.println("回滚事务");
			e.printStackTrace();
		}
	}

	@Override
	public void update(String name) {
		try {
			System.out.println("开启事务");
			userService.update(name);//调用真正业务逻辑方法
			System.out.println("提交事务");
		}catch (Exception e){
			System.out.println("回滚事务");
			e.printStackTrace();
		}
	}

	@Override
	public String findAll(String name) {
		try {
			System.out.println("开启事务");
			String result = userService.findAll(name);//调用真正业务逻辑方法
			System.out.println("提交事务");
			return result;
		}catch (Exception e){
			System.out.println("回滚事务");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String findOne(String id) {
		try {
			System.out.println("开启事务");
			//调用目标类方法
			String one = userService.findOne(id);//调用真正业务逻辑方法
			System.out.println("提交事务");
			return one;
		}catch (Exception e){
			System.out.println("回滚事务");
			e.printStackTrace();
		}
		return null;
	}
}

