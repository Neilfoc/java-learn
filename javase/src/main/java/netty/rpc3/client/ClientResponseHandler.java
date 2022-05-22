package netty.rpc3.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.rpc3.entity.protocal.MsgPkg;
import netty.rpc3.common.Callback;

/**
 * @author neilfoc
 * @Description 客户端处理收到的结果
 * @Date 2022/5/17
 */
public class ClientResponseHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgPkg pkg = (MsgPkg) msg;
        //System.out.println("客户端收到结果");
        Callback.runCallback(pkg);

    }
}
