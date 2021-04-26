package tests.copyTest;

import org.springframework.beans.BeanUtils;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/3
 */
public class SpringBeanUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
}
