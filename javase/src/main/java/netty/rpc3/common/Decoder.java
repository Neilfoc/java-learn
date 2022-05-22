package netty.rpc3.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.rpc3.entity.Constants;
import netty.rpc3.entity.protocal.MsgPkg;
import netty.rpc3.entity.protocal.MyContent;
import netty.rpc3.entity.protocal.MyHeader;
import netty.rpc3.util.SerDerUtil;

import java.util.List;

/**
 * @author neilfoc
 * @Description 服务端和客户端接收的数据都要先解码
 * @Date 2022/5/20
 */
public class Decoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        System.out.println("解码 start：size=" + buf.readableBytes());
        while (buf.readableBytes() >= 101) {//header的长度
            byte[] headerBytes = new byte[101];
            buf.getBytes(buf.readerIndex(), headerBytes); //用get方式不移动下标
            MyHeader header = (MyHeader) SerDerUtil.deserialize(headerBytes);
            System.out.println("解码 header：reqId=" + header.getReqId());

            if (buf.readableBytes() >= header.getDataLen() + 101) {
                buf.readBytes(101);
                // lwx todo: 请求header和响应header里的flag值一般是不一样的
                byte[] dataBytes = new byte[(int) header.getDataLen()];
                buf.readBytes(dataBytes);
                MyContent content = (MyContent) SerDerUtil.deserialize(dataBytes);
                //System.out.println("解码 body：" + content.getName());
                MsgPkg msgPkg = new MsgPkg(header, content);
                out.add(msgPkg);//把请求信息封装后放到out里面
            } else {
                // 这里的body就拆分开了，一部分没放下。
                break;
            }
        }
    }
}
