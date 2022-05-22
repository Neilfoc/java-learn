package netty.rpc3.entity.protocal;

import lombok.Data;

/**
 * @author neilfoc
 * @Description 协议：传输的消息
 * @Date 2022/5/20
 */
@Data
public class MsgPkg {

    private MyHeader header;

    private MyContent content;

    public MsgPkg() {
    }

    public MsgPkg(MyHeader header, MyContent content) {
        this.header = header;
        this.content = content;
    }
}
