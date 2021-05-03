package mybatis.service;

import mybatis.User;
import mybatis.UserDao;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author neilfoc
 * @date 2021/5/3 - 22:21
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    private PlatformTransactionManager transactionManager;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void save(User user) {
        // 创建事务配置对象
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 获取事务状态
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            userDao.save(user);
            //抛出异常
            int i = 1 / 0;
            transactionManager.commit(status);
        }catch (Exception e){
            e.printStackTrace();
            transactionManager.rollback(status);
        }
    }
}
