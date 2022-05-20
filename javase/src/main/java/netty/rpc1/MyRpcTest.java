package netty.rpc1;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc1.handler.ServerRequestHandler;
import netty.rpc1.intf.Car;
import netty.rpc1.proxy.ClientInvocationHandler;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author neilfoc
 * 该版rpc实现了接口的代理缝缀昂，server端可以正常接收到请求并解析出来（需要提前计算header的大小），client端使用countDownLatch来实现等待结果返回
 * 参数设置：客户端数是1，clientPool中连接数也是1，server的线程数也是1
 * 缺点：当出现多个客户端并发时，此时依旧共用一个连接，编解码会出现问题（粘包问题）
 * @Date 2022/5/15
 */
public class MyRpcTest {

    @Test
    public void startServer() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, boss)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client... address=" + JSON.toJSONString(ch.remoteAddress()));
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ServerRequestHandler());
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
        for (int i = 0; i < threads.length; i++) {
            threads[i]=new Thread(()->{
                Car car = getProxy(Car.class);
                car.doThings("hello car");
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Car car = getProxy(Car.class);
        //car.doThings("hello car");
        //Fly fly = getProxy(Fly.class);
        //fly.doThings("hello fly");

    }

    private <T> T getProxy(Class<T> clazz) {
        // 实现类的动态代理
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] classes = {clazz};
        return (T) Proxy.newProxyInstance(classLoader, classes,new ClientInvocationHandler(clazz));
    }


}


