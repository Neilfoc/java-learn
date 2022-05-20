package netty.rpc1.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.rpc1.constants.MyContent;
import netty.rpc1.constants.MyHeader;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @author neilfoc
 * @Description 服务端处理收到的请求
 * @Date 2022/5/17
 */
public class ServerRequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        ByteBuf sendBuf = buf.copy();

        System.out.println("server处理buf：整体size=" + buf.readableBytes());
        if (buf.readableBytes() >= 94) {//header的长度
            byte[] bytes = new byte[94];
            buf.readBytes(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oIn = new ObjectInputStream(in);
            MyHeader header = (MyHeader) oIn.readObject();
            System.out.println("server 收到请求， reqId=" + header.getReqId());

            if (buf.readableBytes() >= header.getDataLen()) {
                System.out.println("处理请求body...");
                byte[] data = new byte[(int) header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);
                MyContent content = (MyContent) doin.readObject();
                System.out.println(content.getName());
            }else{
                System.out.println("server处理buf：取出header 剩下size=" + buf.readableBytes());
            }
        }

        ChannelFuture channelFuture = ctx.writeAndFlush(sendBuf);
        channelFuture.sync();

    }
}
