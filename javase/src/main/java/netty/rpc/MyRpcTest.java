package netty.rpc;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc.handler.ServerRequestHandler;
import netty.rpc.intf.Car;
import netty.rpc.intf.Fly;
import netty.rpc.proxy.ClientInvocationHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author neilfoc
 * @Description
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

        // 并发10个客户端 ？？
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                Car car = getProxy(Car.class);
                car.doThings("hello car");
            }).start();
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


