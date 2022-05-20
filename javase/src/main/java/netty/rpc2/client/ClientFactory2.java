package netty.rpc2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc2.handler.ClientResponseHandler2;
import netty.rpc2.handler.Decoder;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author neilfoc
 * @Description 获取client连接
 * @Date 2022/5/17
 */
public class ClientFactory2 {
    // 客户端连接数的值
    int size = 5;

    Random rand = new Random();

    private static final ClientFactory2 factory;

    static {
        factory = new ClientFactory2();
    }

    public static ClientFactory2 getFactory() {
        return factory;
    }

    ConcurrentHashMap<InetSocketAddress, ClientPool2> outBoxes = new ConcurrentHashMap<>();


    // 加锁
    public synchronized NioSocketChannel getClient(InetSocketAddress address) {
        ClientPool2 clientPool = outBoxes.get(address);
        if (clientPool == null) {
            outBoxes.putIfAbsent(address, new ClientPool2(size));
            clientPool = outBoxes.get(address);
        }
        int i = rand.nextInt(size);

        if (clientPool.clients[i] != null && clientPool.clients[i].isActive()) {
            return clientPool.clients[i];
        }
        synchronized (clientPool.locks[i]) {
            return clientPool.clients[i] = create(address);
        }
    }

    private NioSocketChannel create(InetSocketAddress address) {
        // 基于netty的客户端创建方式
        NioEventLoopGroup clientWorker = new NioEventLoopGroup();
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(clientWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new Decoder());// 加入解码器
                        pipeline.addLast(new ClientResponseHandler2());
                    }
                }).connect(address);
        try {
            NioSocketChannel client = (NioSocketChannel) connect.sync().channel();
            return client;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
