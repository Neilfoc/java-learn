package netty.rpc3.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/22
 */
public class Dispatcher {
    private static Dispatcher dispatcher;

    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    static {
        dispatcher = new Dispatcher();
    }

    public static Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void register(String k, Object v) {
        map.put(k, v);
    }

    public Object getService(String k) {
        return map.get(k);
    }
}
