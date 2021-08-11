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
    // 使用ServletModelAttributeMethodProcessor/ModelAttributeMethodProcessor参数解析器,这个解析器判断参数是否是简单类型
    // WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
    // WebDataBinder 利用它里面的 Converters 将请求数据转成指定的数据类型。再次封装到JavaBean中
    public Person saveuser(Person person){
        return person;
    }
}
