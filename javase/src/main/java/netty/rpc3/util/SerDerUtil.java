package netty.rpc3.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/21
 */
public class SerDerUtil {

    private static ByteArrayOutputStream out = new ByteArrayOutputStream();

    //【必须加锁】
    public synchronized static byte[] serialize(Object msg) {
        out.reset();//重置
        ObjectOutputStream oOut;
        try {
            oOut = new ObjectOutputStream(out);
            oOut.writeObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream oIn = new ObjectInputStream(in);
        return oIn.readObject();

    }
}
