package netty.rpc3.entity;


/**
 * @author neilfoc
 * @Description 这个类只能在同一个jvm中使用，所以其实这个类没啥用
 * @Date 2022/5/21
 */

public class Constants {

    // 记录header的字节数
    private static volatile int headerLength;

    // 核心参数
    public static final int CONSUMER_SIZE = 10;
    public static final int CLIENT_SIZE = 5;
    public static final int EVENT_LOOP_SIZE = 10;



    @Deprecated
    public static int getHeaderLength() {
        return headerLength;
    }

    @Deprecated
    public static void setHeaderLength(int headerLength) {
        System.out.println("set header length: " + headerLength);
        Constants.headerLength = headerLength;
    }


}
