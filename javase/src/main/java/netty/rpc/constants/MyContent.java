package netty.rpc.constants;

import lombok.Data;

import java.io.Serializable;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
@Data
public class MyContent implements Serializable {
    private static final long serialVersionUID = -1179269797340785219L;
    String name;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] args;
}
