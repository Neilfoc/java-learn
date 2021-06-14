package com.neilfoc.boot.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11105157
 * @Description
 * @Date 2021/6/14
 */
@Controller
public class RequestForwardController {

    @RequestMapping("/goto")
    public String gotoForward(HttpServletRequest request) {
        request.setAttribute("msg", "转发");
        request.setAttribute("code", "success");
        return "forward:/success";
    }

    @GetMapping("/params")
    public String complexParam(Map<String, Object> map,
                               Model model,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        map.put("attr1", "map");
        model.addAttribute("attr2", "model");
        request.setAttribute("attr3", "httpServletRequest");
        Cookie cookie = new Cookie("k", "v");
        response.addCookie(cookie);
        return "forward:/success";
    }

    @RequestMapping("/success")
    @ResponseBody
    public Map<String, Object> success(@RequestAttribute(value = "msg", required = false) String msg,
                                       HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        //map.put("request", request);//写这一行会报错
        //map.put("request", JSON.toJSONString(request));//写这一行也会报错
        map.put("request", request.getAttribute("msg"));
        map.put("attr1", request.getAttribute("attr1"));
        map.put("attr2", request.getAttribute("attr2"));
        map.put("attr3", request.getAttribute("attr3"));
        return map;
    }
}
