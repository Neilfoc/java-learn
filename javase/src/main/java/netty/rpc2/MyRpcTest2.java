package netty.rpc2;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc2.handler.ServerRequestHandler2;
import netty.rpc1.intf.Car;
import netty.rpc2.handler.Decoder;
import netty.rpc2.proxy.ClientInvocationHandler2;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author neilfoc
 * 该版rpc实现了接口的代理，server端可以正常接收到请求并解析出来（需要提前计算header的大小），client端使用completableFuture来实现等待结果返回
 * 解决了粘包问题，服务端和客户端收到的数据都进行解码操作
 * 参数设置：客户端数，clientPool中连接数，server的线程数
 * @Date 2022/5/15
 */
public class MyRpcTest2 {

    public void startServer() {
        NioEventLoopGroup boss = new NioEventLoopGroup(10);
        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, boss)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client... address=" + JSON.toJSONString(ch.remoteAddress()));
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new Decoder());
                        pipeline.addLast(new ServerRequestHandler2());
                    }
                }).bind(new InetSocketAddress("localhost", 9090));

        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRpc() {
        new Thread(this::startServer).start();
        System.out.println("server start...");

        // 并发10个客户端
        // 服务端解析butebuf转成对象报错
        Thread[] threads = new Thread[10];
        AtomicInteger num = new AtomicInteger(0);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                Car car = getProxy(Car.class);
                String req = "hello car" + num.incrementAndGet();
                String result = car.doThings(req);// 方法有返回值
                System.out.println("请求：" + req + ", 返回: " + result);
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

        //Car car = getProxy(Car.class); //用来debug验证header的大小
        //car.doThings("hello car");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private <T> T getProxy(Class<T> clazz) {
        // 实现类的动态代理
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] classes = {clazz};
        return (T) Proxy.newProxyInstance(classLoader, classes, new ClientInvocationHandler2(clazz));
    }


}


