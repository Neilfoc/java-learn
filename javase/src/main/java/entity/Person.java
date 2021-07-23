package entity;

import lombok.Data;

/**
 * @author 11105157
 * @Description
 * @Date 2020/12/19
 */
@Data
public class Person {
    private int age;

    private String name;

    private double money;

    public Person(int age, String name, double money) {
        this.age = age;
        this.name = name;
        this.money = money;
    }
}
