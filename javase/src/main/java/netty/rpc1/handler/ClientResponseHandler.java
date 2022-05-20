package netty.rpc1.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.rpc1.constants.MyHeader;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @author neilfoc
 * @Description 客户端处理收到的结果
 * @Date 2022/5/17
 */
public class ClientResponseHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        if (buf.readableBytes() >= 94) { //header的长度
            byte[] bytes = new byte[94];
            buf.readBytes(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oIn = new ObjectInputStream(in);
            MyHeader header = (MyHeader) oIn.readObject();
            System.out.println("client 收到结果， reqId=" + header.getReqId());
            // 客户端收到结果后需要调整countDownLatch，使得主线程能运行
            ResponseHandler.runCallback(header.getReqId());
        }
    }
}
