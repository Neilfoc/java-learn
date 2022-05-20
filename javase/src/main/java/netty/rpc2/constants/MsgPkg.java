package netty.rpc2.constants;

import lombok.Data;
import netty.rpc1.constants.MyContent;
import netty.rpc1.constants.MyHeader;

/**
 * @author neilfoc
 * @Description
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
