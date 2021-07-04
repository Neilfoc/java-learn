package com.neilfoc.boot.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //  user/2/owner?p1=1&p2=3
    @GetMapping("/user/{id}/owner")
    public Map<String, Object> fun01(@PathVariable("id") Integer id,
                                     @PathVariable Map<String, String> pv,
                                     @RequestHeader Map<String, String> header,
                                     @RequestParam MultiValueMap<String, String> params,
                                     @CookieValue(value = "JSESSIONID",required = false) Cookie cookie) {

        Map<String, Object> map = new HashMap<>();


        map.put("id", id);
        map.put("map", pv);
        map.put("header", header);
        map.put("params", params);
        System.out.println(JSON.toJSONString(cookie));
        return map;
    }

    @PostMapping("/save")
    public Map<String, Object> postMethod(@RequestBody String content) { //@RequestBody将参数值组成a=1&b=2的形式返回给content
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        return map;
    }

    //1、语法： 请求路径：/cars/sell;low=34;brand=byd,audi,yd
    //2、SpringBoot默认是禁用了矩阵变量的功能
    //      手动开启：原理。对于路径的处理。UrlPathHelper进行解析。
    //              removeSemicolonContent（会移除分号后内容），设置为false支持矩阵变量
    //3、矩阵变量必须有url路径变量才能被解析
    @GetMapping("/cars/{path}")
    public Map carsSell(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> brand,
                        @PathVariable("path") String path) {
        Map<String, Object> map = new HashMap<>();

        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }


}
