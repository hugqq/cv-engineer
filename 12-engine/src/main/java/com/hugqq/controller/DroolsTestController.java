package com.hugqq.controller;

import com.hugqq.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DroolsTestController {

    @Autowired
    private KieSession kieSession;

    @GetMapping("drools/test")
    public Person test(Person person){
        kieSession.insert(person);
        int count = kieSession.fireAllRules();
        log.info("总执行了{}条规则", count);
        return person;
    }

}
