package tests.copyTest;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/3
 */
public class CommonsBeanUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        BeanUtils.copyProperties(target, source);
    }
}
