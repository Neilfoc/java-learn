package entity;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/9/4
 */
public class SonOrder extends Order {

    public SonOrder(){
        System.out.println("SonOrder 子订单 无参构造函数");
    }

    public SonOrder(String desc){
        this();
        System.out.println("SonOrder 子订单 有参构造函数 "+desc);
    }
}
