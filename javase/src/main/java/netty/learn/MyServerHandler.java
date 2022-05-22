package netty.learn;

/**
 * @author 11105157
 * @Description
 * @Date 2022/4/11
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义的Handler需要继承Netty规定好的HandlerAdapter
 * 才能被Netty框架所关联，有点类似SpringMVC的适配器模式
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到客户端 " + ctx.channel().remoteAddress() + " 发送的消息： " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //发送消息给客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        ctx.close();
    }

    // taskQueue任务队列
    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取到线程池eventLoop，添加线程，执行
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //长时间操作，不至于长时间的业务操作导致Handler阻塞
                    Thread.sleep(1000);
                    System.out.println("长时间的业务处理");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    // scheduleTaskQueue延时任务队列
    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    //长时间操作，不至于长时间的业务操作导致Handler阻塞
                    Thread.sleep(1000);
                    System.out.println("长时间的业务处理");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },5, TimeUnit.SECONDS);//5秒后执行
    }*/

    // Future异步机制
}
