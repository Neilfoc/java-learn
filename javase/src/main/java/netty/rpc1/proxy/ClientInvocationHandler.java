package netty.rpc1.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.rpc1.client.ClientFactory;
import netty.rpc1.constants.MyContent;
import netty.rpc1.constants.MyHeader;
import netty.rpc1.handler.ResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
public class ClientInvocationHandler implements InvocationHandler {

    private Class<?> clazz;

    public ClientInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1.协议封装：将请求数据封装成message：header和body
        MyContent content = createMyContent(method, args);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oOut = new ObjectOutputStream(out);
        oOut.writeObject(content);
        byte[] msgBody = out.toByteArray();

        MyHeader header = createMyHeader(msgBody);  // 每个请求要有唯一id + message
        out.reset();
        oOut = new ObjectOutputStream(out);
        oOut.writeObject(header);
        byte[] msgHeader = out.toByteArray();

        // 2.客户端连接池，获得客户端连接
        ClientFactory factory = ClientFactory.getFactory();
        NioSocketChannel client = factory.getClient(new InetSocketAddress("localhost", 9090));

        // 发送数据
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
        long reqId = header.getReqId();
        ResponseHandler.addCallback(reqId, () -> {
            System.out.println("调起callback，放行主线程...");
            countDownLatch.countDown();
        });
        byteBuf.writeBytes(msgHeader);
        byteBuf.writeBytes(msgBody);
        ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
        channelFuture.sync();

        // 需要等到服务端返回处理结果（怎么能在server返回后才往下执行）
        countDownLatch.await();
        System.out.println("主线程执行...");
        return null;
    }

    private MyContent createMyContent(Method method, Object[] args) {
        MyContent content = new MyContent();
        String name = clazz.getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        content.setArgs(args);
        content.setName(name);
        content.setMethodName(methodName);
        content.setParameterTypes(parameterTypes);
        return content;
    }

    private MyHeader createMyHeader(byte[] msg) {
        MyHeader header = new MyHeader();
        header.setFlag(0x14141414);
        header.setReqId(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
        header.setDataLen(msg.length);

        return header;
    }
}
