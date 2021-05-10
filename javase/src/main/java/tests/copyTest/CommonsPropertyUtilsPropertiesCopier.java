package tests.copyTest;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/3
 */
public class CommonsPropertyUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        PropertyUtils.copyProperties(target, source);
    }
}
