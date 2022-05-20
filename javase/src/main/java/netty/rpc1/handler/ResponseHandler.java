package netty.rpc1.handler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
public class ResponseHandler {
    static ConcurrentHashMap<Long, Runnable> mapping = new ConcurrentHashMap<>();


    public static void addCallback(long reqId, Runnable cb) {
        mapping.putIfAbsent(reqId, cb);
    }

    public static void runCallback(long reqId) {
        Runnable runnable = mapping.get(reqId);
        runnable.run();
        removeCb(reqId);
    }

    private static void removeCb(long reqId) {
        mapping.remove(reqId);
    }
}
