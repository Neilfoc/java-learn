package mybatis.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author neilfoc
 * @date 2021/5/3 - 23:44
 */
public class TransactionAdvice implements MethodInterceptor {

    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 创建事务配置对象
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 获取事务状态
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            System.out.println("执行advice功能，前置通知");
            Object proceed = methodInvocation.proceed();
            transactionManager.commit(status);
            System.out.println("执行advice功能，后置通知");
            return proceed;
        } catch (Exception e) {
            System.out.println("执行advice功能，异常通知");
            e.printStackTrace();
            transactionManager.rollback(status);
        }
        return null;
    }



}
