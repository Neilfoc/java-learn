package spi.impl;

import spi.Log;

/**
 * @author 11105157
 * @Description
 * @Date 2022/2/3
 */
public class Log4J implements Log {

    @Override
    public String log(String value) {
        System.out.println("Log4J: " + value);
        return "ok";
    }
}
