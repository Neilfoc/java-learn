package com.neilfoc.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 11105157
 * @Description
 * @Date 2021/6/13
 */
@RestController
public class HelloController {

    @RequestMapping("/41.jpg")
    public String hello() {
        return "haha";
    }

    //@RequestMapping(value = "/user", method = RequestMethod.GET)
    @GetMapping("/user")
    public String getUser() {
        return "GET-张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser() {
        return "POST-张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String putUser() {
        return "PUT-张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteUser() {
        return "DELETE-张三";
    }
}
