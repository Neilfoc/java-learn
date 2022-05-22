package netty.rpc3;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc3.common.Decoder;
import netty.rpc3.common.Dispatcher;
import netty.rpc3.common.impl.MyCar;
import netty.rpc3.common.impl.MyFly;
import netty.rpc3.common.intf.Car;
import netty.rpc3.common.intf.Fly;
import netty.rpc3.entity.Constants;
import netty.rpc3.proxy.ProxyFactory;
import netty.rpc3.server.ServerRequestHandler;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author neilfoc
 * 该版rpc实现了接口的代理，server端可以正常接收到请求并解析出来（需要提前计算header的大小），client端使用completableFuture来实现等待结果返回
 * 加入dispatcher，代码重构优化
 * 参数设置：客户端数，clientPool中连接数，server的线程数
 * @Date 2022/5/15
 */
public class MyRpcTest {

    @Test
    public void startServer() {
        // 注册服务
        MyCar myCar = new MyCar();
        MyFly myFly = new MyFly();
        Dispatcher dispatcher = Dispatcher.getDispatcher();
        dispatcher.register(Car.class.getName(), myCar);
        dispatcher.register(Fly.class.getName(), myFly);

        NioEventLoopGroup boss = new NioEventLoopGroup(Constants.EVENT_LOOP_SIZE);
        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, boss)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client... address=" + JSON.toJSONString(ch.remoteAddress()));
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new Decoder());
                        pipeline.addLast(new ServerRequestHandler());
                    }
                }).bind(new InetSocketAddress("localhost", 9090));

        try {
            System.out.println("server start...");
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void consume() {
        // 同一个jvm的调用
        //new Thread(this::startServer).start();
        //System.out.println("server start...");

        // 并发10个客户端
        Thread[] threads = new Thread[Constants.CONSUMER_SIZE];
        AtomicInteger num = new AtomicInteger(0);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                Car car = ProxyFactory.getProxy(Car.class);
                String req = "car" + num.incrementAndGet();
                String result = car.doThings(req);// 方法有返回值
                System.out.println("请求：" + req + ", 返回: " + result);
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

    }


}


