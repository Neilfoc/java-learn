package netty.rpc3.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc3.client.ClientResponseHandler;
import netty.rpc3.common.Callback;
import netty.rpc3.common.Decoder;
import netty.rpc3.entity.Constants;
import netty.rpc3.entity.protocal.MyContent;
import netty.rpc3.entity.protocal.MyHeader;
import netty.rpc3.util.SerDerUtil;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author neilfoc
 * @Description client工具类：获取连接,传输数据
 * @Date 2022/5/17
 */
public class ConnectionUtil {
    // 连接池大小
    int size = Constants.CLIENT_SIZE;

    Random rand = new Random();

    private static final ConnectionUtil factory;

    static {
        factory = new ConnectionUtil();
    }

    public static ConnectionUtil getFactory() {
        return factory;
    }

    ConcurrentHashMap<InetSocketAddress, ConnectionPool> clientPoolMap = new ConcurrentHashMap<>();

    // 加锁
    public NioSocketChannel getClient(InetSocketAddress address) {
        ConnectionPool connectionPool = clientPoolMap.get(address);
        // 双重校验锁
        if (connectionPool == null) {
            synchronized (clientPoolMap) {
                if (connectionPool == null) {
                    clientPoolMap.putIfAbsent(address, new ConnectionPool(size));
                    connectionPool = clientPoolMap.get(address);
                }
            }
        }
        int i = rand.nextInt(size);

        if (connectionPool.clients[i] == null || !connectionPool.clients[i].isActive()) {
            synchronized (connectionPool.locks[i]) {
                if (connectionPool.clients[i] == null || !connectionPool.clients[i].isActive()) {
                    connectionPool.clients[i] = create(address);
                }
            }
        }
        return connectionPool.clients[i];
    }

    public static CompletableFuture transport(MyContent content) throws Exception {
        // 1.协议封装：将请求数据封装成message：header和body
        byte[] bodyBytes = SerDerUtil.serialize(content);
        // 每个请求要有唯一id + message
        MyHeader header = MyHeader.createMyHeader(bodyBytes, 0x14141414, Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        byte[] headerBytes = SerDerUtil.serialize(header);

        // 2.客户端连接池，获得客户端连接
        NioSocketChannel client = factory.getClient(new InetSocketAddress("localhost", 9090));

        // 3.发送数据
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + bodyBytes.length);
        byteBuf.writeBytes(headerBytes);
        byteBuf.writeBytes(bodyBytes);
        long reqId = header.getReqId();
        CompletableFuture<Object> res = new CompletableFuture<>();
        Callback.addCallback(reqId, res);
        ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
        channelFuture.sync();

        return res;
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
                        pipeline.addLast(new ClientResponseHandler());
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
