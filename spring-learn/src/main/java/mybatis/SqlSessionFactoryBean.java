package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.io.InputStream;

// 实现Spring对复杂对象的创建，创建SqlSessionFactory，
// 但是mybatis已经实现了不用自己来实现，只需要导入mybatis-spring的jar包
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory> {
	@Override
	public SqlSessionFactory getObject() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	@Override
	public Class<?> getObjectType() {
		return SqlSessionFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
