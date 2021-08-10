package com.neilfoc.boot.controller;

import com.neilfoc.boot.object.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/8/10
 */
@RestController
// 测试自定义对象参数
public class CustomParameterTestController {

    @PostMapping("/saveuser")
    public Person saveuser(Person person){
        return person;
    }
}
