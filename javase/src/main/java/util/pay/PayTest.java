package util.pay;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/24
 */
public class PayTest {
    public static void main(String[] args) {
        String pay = "ali";
        /*if (pay.equals("ALIPAY")) {
            new ZhifubaoPay().process();
        } else if (pay.equals("WECHATPAY")) {
            new WechatPay().process();
        } else {
            new YinlianPay().process();
        }*/
        PayChannelEnum payChannelEnum = PayChannelEnum.matchPayName(pay);
        if (payChannelEnum != null) {
            payChannelEnum.payChannel.process();
        }
    }
}
