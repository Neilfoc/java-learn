package com.neilfoc.boot.controller;

import com.neilfoc.boot.object.Person;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author 11105157
 * @Description
 * @Date 2021/8/13
 */
@Controller
public class ResponseTestController {

    @ResponseBody  //利用返回值处理器里面的消息转换器进行处理
    @GetMapping(value = "/test/person")
    public Person getPerson() {
        // 最终使用 MappingJackson2HttpMessageConverter 消息转换器
        Person person = new Person();
        person.setAge(28);
        person.setBirth(new Date());
        person.setUserName("zhangsan");
        return person;
    }

    @ResponseBody  // 主要是用来测试最终使用哪个消息转换器
    @GetMapping("/test/file")
    public FileSystemResource getfile() {
        // 最终使用的是ResourceHttpMessageConverter消息转换器
        FileSystemResource fileSystemResource = new FileSystemResource("C:\\Users\\11105157\\Pictures\\4.jpg");
        return fileSystemResource;
    }
}
