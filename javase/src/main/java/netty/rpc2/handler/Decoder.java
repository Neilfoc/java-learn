package netty.rpc2.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.rpc1.constants.MyContent;
import netty.rpc1.constants.MyHeader;
import netty.rpc2.constants.Constants;
import netty.rpc2.constants.MsgPkg;
import netty.rpc2.util.SerDerUtil;

import java.util.List;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/20
 */
public class Decoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        System.out.println("解码 start：size=" + buf.readableBytes());
        while (buf.readableBytes() >= Constants.getHeaderLength()) {//header的长度
            byte[] headerBytes = new byte[Constants.getHeaderLength()];
            buf.getBytes(buf.readerIndex(), headerBytes); //用get方式不移动下标
            MyHeader header = (MyHeader) SerDerUtil.deserialize(headerBytes);
            System.out.println("解码 header：reqId=" + header.getReqId());

            if (buf.readableBytes() >= header.getDataLen() + Constants.getHeaderLength()) {
                buf.readBytes(Constants.getHeaderLength());
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
