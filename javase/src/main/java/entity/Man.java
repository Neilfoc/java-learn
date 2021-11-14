package entity;

import lombok.Data;

/**
 * @author 11105157
 * @Description
 * @Date 2021/8/28
 */
@Data
public class Man {
    private int age;

    private String name;

    private String money;

    public Man(int age, String name, String money) {
        this.age = age;
        this.name = name;
        this.money = money;
    }
}
