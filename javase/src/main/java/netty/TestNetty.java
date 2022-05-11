package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/11
 */
public class TestNetty {

    @Test
    // 客户端
    public void clientMode() throws Exception {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);

        NioSocketChannel client = new NioSocketChannel();
        thread.register(client); //在netty中 client也需要注册到多路复用器上 这个对应epoll_ctl

        //【读】
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new MyInHandler()); //响应式，有读事件才会调用handler

        //netty reactor异步的特征
        ChannelFuture connect = client.connect(new InetSocketAddress("192.168.1.4", 9090));//服务端可以使用nc -l 192.168.1.4 9090起服务
        ChannelFuture sync = connect.sync();

        //【写】
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world".getBytes());
        ChannelFuture send = client.writeAndFlush(byteBuf);
        send.sync();

        sync.channel().closeFuture().sync();
        System.out.println("client connect closed...");
    }

    @Test
    public void serverMode() throws Exception {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();

        thread.register(server);
        ChannelPipeline pipeline = server.pipeline();
        pipeline.addLast(new MyAcceptHandler(thread,new ChannelInitHandler()));
        ChannelFuture connect = server.bind(new InetSocketAddress("192.168.1.3", 9090));

        connect.sync().channel().closeFuture().sync();
        System.out.println("server connect closed...");

    }
}



//@ChannelHandler.Sharable //这个是在只有一个handler得情况下每个client共享，但是这样成员变量也共享了,所以这个handler需要时单例的不能共享。
//报错：MyInHandler is not a @Sharable handler, so can't be added or removed multiple times
//accept事件handler
class MyInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client registered...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        // read会移动buf指针 get不会
        //CharSequence charSequence = buf.readCharSequence(buf.readableBytes(), CharsetUtil.UTF_8);
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(charSequence);
        ctx.writeAndFlush(buf);
    }
}

//accept事件handler
class MyAcceptHandler extends ChannelInboundHandlerAdapter{
    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler handler) {
        this.selector = thread;
        this.handler = handler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client registered...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel client = (SocketChannel) msg;
        // accept得到客户端后，还需要做两件事
        // 0.初始handler
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(handler); //1.client::pipeline[ChannelInitHandler]
        // 1.向复用器注册读事件
        selector.register(client);
        // 2.响应式handler
        //ChannelPipeline pipeline = client.pipeline();
        //pipeline.addLast(handler);
    }
}


// 初始handler 每个client注册读事件时都可以单独得搞一个MyInHandler
// MyInHandler不用加 @Sharable注解了
@ChannelHandler.Sharable
class ChannelInitHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {//有注册事件就会调这个接口
        Channel client = ctx.channel();
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new MyInHandler());//2.client::pipeline[ChannelInitHandler,MyInHandler]
        pipeline.remove(this);//3.client::pipeline[MyInHandler]
        System.out.println("new MyInHandler...");
    }
}
