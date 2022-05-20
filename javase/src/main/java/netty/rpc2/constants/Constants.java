package netty.rpc2.constants;


/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/21
 */
public class Constants {

    // 记录header的字节数
    private static volatile int headerLength;

    public static int getHeaderLength() {
        return headerLength;
    }

    public static void setHeaderLength(int headerLength) {
        //System.out.println("set header length: " + headerLength);
        Constants.headerLength = headerLength;
    }
}
