package object;

import lombok.*;

/**
 * @author 11105157
 * @Description
 * @Date 2020/11/14
 */
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private long id;
    private long customerId;
    private String customerName;
    private double totalPrice;
    private Person person;
    public Order(long id,long customerId,String customerName,double totalPrice){
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }
    /*public double getTotalPrice(){
        return this.totalPrice;
    }
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }*/
}
