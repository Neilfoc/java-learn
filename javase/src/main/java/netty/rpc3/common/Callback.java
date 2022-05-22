package netty.rpc3.common;

import com.alibaba.fastjson.JSON;
import netty.rpc3.entity.protocal.MsgPkg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author neilfoc
 * @Description 回调函数，客户端请求和服务端响应可以做到【异步】，通过一个reqId建立联系
 * @Date 2022/5/17
 */
public class Callback {

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
