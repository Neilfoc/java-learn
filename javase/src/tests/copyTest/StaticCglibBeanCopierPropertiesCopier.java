package tests.copyTest;

import net.sf.cglib.beans.BeanCopier;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/3
 */

public class StaticCglibBeanCopierPropertiesCopier implements PropertiesCopier {

    // 全局静态 BeanCopier，避免每次都生成新的对象，不使用converter
    private static BeanCopier copier = BeanCopier.create(Account.class, Account.class, false);

    @Override
    public void copyProperties(Object source, Object target) {
        copier.copy(source, target, null);
    }
}

