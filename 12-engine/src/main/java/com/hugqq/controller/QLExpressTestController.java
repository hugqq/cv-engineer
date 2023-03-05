package com.hugqq.controller;

import com.hugqq.entity.Person;
import com.hugqq.utils.QlExpressUtil;
import com.ql.util.express.DefaultContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class QLExpressTestController {


    @Autowired
    QlExpressUtil qlExpressUtil;

    @SneakyThrows
    @GetMapping("ql/test")
    public void test(){

        String express = "如果(超过18岁) 则 { return true; } 否则 { return false; }";

        DefaultContext<String, Object> context = new DefaultContext<>();
        Person person = new Person();
        person.setName("zhangsan");
        person.setAge(20);
        context.put("person",person);
        Object execute = qlExpressUtil.execute(express, context);
        person.setPlayGame((Boolean) execute);
        log.info("person1 {}",person);

        Person person2 = new Person();
        person2.setName("lisi");
        person2.setAge(17);
        context.put("person",person2);
        Object execute2 = qlExpressUtil.execute(express, context);
        person2.setPlayGame((Boolean) execute2);
        log.info("person2 {}",person2);

    }

}
