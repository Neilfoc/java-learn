package aop.proxy;

public class UserServiceImpl implements UserService {
	@Override
	public void save(String name) {
		System.out.println("处理业务逻辑,调用DAO~~~" + name);
	}

	@Override
	public void delete(String id) {
		System.out.println("处理业务逻辑,调用DAO~~~" + id);
	}

	@Override
	public void update(String name) {
		System.out.println("处理业务逻辑,调用DAO~~~" + name);
	}

	@Override
	public String findAll(String name) {
		System.out.println("处理业务逻辑,调用DAO~~~" + name);
		return name;
	}

	@Override
	public String findOne(String id) {
		System.out.println("处理业务逻辑,调用DAO~~~" + id);
		return id;
	}
}

