package netty.rpc3.proxy;


import java.lang.reflect.Proxy;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/22
 */
public class ProxyFactory {

    public static <T> T getProxy(Class<T> clazz) {
        // 实现类的动态代理
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] classes = {clazz};
        return (T) Proxy.newProxyInstance(classLoader, classes, new ProxyInvocationHandler(clazz));
    }
}
