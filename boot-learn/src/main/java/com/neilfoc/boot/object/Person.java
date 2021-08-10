package com.neilfoc.boot.object;

import lombok.Data;

import java.util.Date;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/8/10
 */
@Data
public class Person {

    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;
}
