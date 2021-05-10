package util.pay;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/24
 */
public class WechatPay extends PayChannel {
    @Override
    public void process() {
        System.out.println("微信支付");
    }
}
