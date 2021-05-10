package util.pay;

import lombok.Getter;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/24
 */
//@Getter
public enum PayChannelEnum {
    ALIPAY("ali",new ZhifubaoPay()),
    WECHATPAY("wechat",new WechatPay()),
    YINLAINPAY("yinlian",new YinlianPay()),
    ;

    String payName;
    PayChannel payChannel;

    PayChannelEnum(String payName, PayChannel payChannel) {
        this.payName = payName;
        this.payChannel = payChannel;
    }

    public static PayChannelEnum matchPayName(String payName) {
        PayChannelEnum[] payChannelEnums = PayChannelEnum.values();
        for (PayChannelEnum payChannelEnum : payChannelEnums) {
            if (payChannelEnum.payName.equals(payName)) {
                return payChannelEnum;
            }
        }
        return null;
    }
}
