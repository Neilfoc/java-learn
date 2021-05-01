package aop.proxy;

public interface UserService {
	public void save(String name);

	public void delete(String name);

	public void update(String name);

	public String findAll(String name);

	public String findOne(String name);
}
