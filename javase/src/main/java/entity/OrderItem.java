package entity;

import lombok.Data;

/**
 * @author 11105157
 * @Description
 * @Date 2020/11/14
 */
@Data
public class OrderItem {
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer productQuantity;
}
