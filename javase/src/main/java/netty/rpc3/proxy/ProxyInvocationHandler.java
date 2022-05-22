package netty.rpc3.proxy;

import netty.rpc3.common.Dispatcher;
import netty.rpc3.entity.protocal.MyContent;
import netty.rpc3.transport.ConnectionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private Class<?> clazz;

    public ProxyInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        Dispatcher dispatcher = Dispatcher.getDispatcher();
        Object service = dispatcher.getService(clazz.getName());
        if (service != null) { //fc 本地调用
            System.out.println("local call...");
            Class<?> serviceClass = service.getClass();
            Method serviceMethod = serviceClass.getMethod(method.getName(), method.getParameterTypes());
            result = serviceMethod.invoke(service, args);
        } else { //rpc 远程调用
            System.out.println("remote call...");
            MyContent content = createMyContent(method, args);
            CompletableFuture future = ConnectionUtil.transport(content);

            // 需要等到服务端返回处理结果（怎么能在server返回后才往下执行）
            result = future.get();//这里阻塞等待结果返回
        }
        return result;
    }

    private MyContent createMyContent(Method method, Object[] args) {
        MyContent content = new MyContent();
        String name = clazz.getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        content.setArgs(args);
        content.setName(name);
        content.setMethodName(methodName);
        content.setParameterTypes(parameterTypes);
        return content;
    }

}
