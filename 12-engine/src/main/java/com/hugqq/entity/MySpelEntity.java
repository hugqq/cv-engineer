package com.hugqq.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class MySpelEntity {


    private String name;

    @Value("spel打印-->>>%{@name}")
    private String desc;


;

}
