package tests.copyTest;

import lombok.Data;

/**
 * @author 11105157
 * @Description
 * @Date 2020/12/19
 */
@Data
public class Account {
    private int age;

    private String name;

    private double money;

    private Integer h;

    public Account(){}

    Account(int age, String name, double money) {
        this.age = age;
        this.name = name;
        this.money = money;
    }
}
