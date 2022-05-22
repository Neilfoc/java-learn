package netty.rpc3.common.impl;

import netty.rpc3.common.intf.Car;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/22
 */
public class MyCar implements Car {
    @Override
    public String doThings(String msg) {
        return msg + " running";
    }
}
