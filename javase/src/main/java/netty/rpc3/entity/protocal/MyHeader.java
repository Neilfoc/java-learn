package netty.rpc3.entity.protocal;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author neilfoc
 * @Description 协议头
 * @Date 2022/5/17
 */
@Data
public class MyHeader implements Serializable {
    private static final long serialVersionUID = -9004221165343061238L;
    private int flag; //用一个32位的int，可以记录很多信息
    private long reqId;
    private long dataLen;

    public static MyHeader createMyHeader(byte[] msg, int flag, long reqId) {
        MyHeader header = new MyHeader();
        header.setFlag(flag);
        header.setReqId(reqId);
        header.setDataLen(msg.length);

        return header;
    }
}
