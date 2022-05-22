package netty.rpc3.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.rpc3.common.Dispatcher;
import netty.rpc3.entity.protocal.MsgPkg;
import netty.rpc3.entity.protocal.MyContent;
import netty.rpc3.entity.protocal.MyHeader;
import netty.rpc3.util.SerDerUtil;

import java.lang.reflect.Method;


/**
 * @author neilfoc
 * @Description 服务端处理收到的请求
 * @Date 2022/5/17
 */
public class ServerRequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgPkg pkg = (MsgPkg) msg;
        String ioThreadName = Thread.currentThread().getName();

        Dispatcher dispatcher = Dispatcher.getDispatcher();
        //需要给客户端返回结果
        // 1.header中的flag值更新
        // 2.业务处理和返回的实现方式：（当前方法中，使用当前的eventLoop，用父执行器分配一个eventLoop）
        //ctx.executor().execute(()->{
        ctx.executor().parent().next().execute(() -> {
            //String execThreadName = Thread.currentThread().getName();
            //String result = "io thread: " + ioThreadName + ", exec thread: " + execThreadName + ", from args: " + pkg.getContent().getArgs()[0].toString();
            MyContent resContent = new MyContent();
            Object service = dispatcher.getService(pkg.getContent().getName());
            Class<?> serviceClass = service.getClass();
            try {
                Method method = serviceClass.getMethod(pkg.getContent().getMethodName(), pkg.getContent().getParameterTypes());
                Object result = method.invoke(service, pkg.getContent().getArgs());
                resContent.setResult(result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] bodyBytes = SerDerUtil.serialize(resContent);
            MyHeader resHeader = MyHeader.createMyHeader(bodyBytes, 0x14141424, pkg.getHeader().getReqId());
            byte[] headerBytes = SerDerUtil.serialize(resHeader);

            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + bodyBytes.length);
            byteBuf.writeBytes(headerBytes);
            byteBuf.writeBytes(bodyBytes);
            ctx.writeAndFlush(byteBuf);
        });
    }
}
