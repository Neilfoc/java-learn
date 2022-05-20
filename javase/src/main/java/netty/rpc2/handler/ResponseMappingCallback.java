package netty.rpc2.handler;

import netty.rpc2.constants.MsgPkg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
public class ResponseMappingCallback {
    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();


    public static void addCallback(long reqId, CompletableFuture cb) {
        mapping.putIfAbsent(reqId, cb);
    }

    public static void runCallback(MsgPkg pkg) {
        CompletableFuture future = mapping.get(pkg.getHeader().getReqId());
        future.complete(pkg.getContent().getResult());
        removeCb(pkg.getHeader().getReqId());
    }

    //public static void addCallback(long reqId, Runnable cb) {
    //    mapping.putIfAbsent(reqId, cb);
    //}
    //
    //public static void runCallback(long reqId) {
    //    Runnable runnable = mapping.get(reqId);
    //    runnable.run();
    //    removeCb(reqId);
    //}

    private static void removeCb(long reqId) {
        mapping.remove(reqId);
    }
}
