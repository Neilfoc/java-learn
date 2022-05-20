package netty.rpc2.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.rpc1.constants.MyContent;
import netty.rpc1.constants.MyHeader;
import netty.rpc2.constants.MsgPkg;
import netty.rpc2.util.SerDerUtil;


/**
 * @author neilfoc
 * @Description 服务端处理收到的请求
 * @Date 2022/5/17
 */
public class ServerRequestHandler2 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgPkg pkg = (MsgPkg) msg;
        String ioThreadName = Thread.currentThread().getName();

        //System.out.println(pkg.getContent().getArgs()[0]);

        //需要给客户端返回结果
        // 1.header中的flag值更新
        // 2.业务处理和返回的实现方式：（当前方法中，使用当前的eventLoop，用父执行器分配一个eventLoop）
        //ctx.executor().execute(()->{
        ctx.executor().parent().next().execute(() -> {
            String execThreadName = Thread.currentThread().getName();
            MyContent content = new MyContent();
            String s = "io thread: " + ioThreadName + ", exec thread: " + execThreadName + ", from args: " + pkg.getContent().getArgs()[0].toString();
            content.setResult(s);
            //System.out.println(s);
            byte[] bodyBytes = SerDerUtil.serialize(content);
            MyHeader resHeader = new MyHeader();
            resHeader.setFlag(0x14141424);
            resHeader.setReqId(pkg.getHeader().getReqId());
            resHeader.setDataLen(bodyBytes.length);
            byte[] headerBytes = SerDerUtil.serialize(resHeader);

            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + bodyBytes.length);
            byteBuf.writeBytes(headerBytes);
            byteBuf.writeBytes(bodyBytes);
            ctx.writeAndFlush(byteBuf);
        });
    }
}
