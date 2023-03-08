package com.hugqq.service;

import com.hugqq.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QLExpressTestService {


    public boolean isOver18(Person person, int age) {
        if (person.getAge() >= 18) {
            return true;
        }
        return false;
    }


}
