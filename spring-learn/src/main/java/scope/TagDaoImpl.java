package scope;

public class TagDaoImpl implements TagDao {
	@Override
	public void save(String name) {
		System.out.println("name is :" + name);
	}

	public void init(){
		System.out.println("初始化");
	}

	public void destroy(){
		System.out.println("销毁");
	}
}
