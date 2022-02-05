package spi.impl;

import spi.Log;

/**
 * @author 11105157
 * @Description
 * @Date 2022/2/3
 */
public class Logback implements Log {
    @Override
    public String log(String value) {
        System.out.println("Logback: " + value);
        return "ok";
    }
}
