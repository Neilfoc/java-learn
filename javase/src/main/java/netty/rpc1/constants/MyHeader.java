package netty.rpc1.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
@Data
public class MyHeader implements Serializable {
    private static final long serialVersionUID = -9004221165343061238L;
    private int flag; //用一个32位的int，可以记录很多信息
    private long reqId;
    private long dataLen;
}
