package mybatis;

import java.util.List;

public interface UserDao {
	//查询所有
	List<User> findAll();

	void save(User user);
}

