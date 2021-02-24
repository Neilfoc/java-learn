package tests.copyTest;

import net.sf.cglib.beans.BeanCopier;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/3
 */
public class CglibBeanCopierPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }
}