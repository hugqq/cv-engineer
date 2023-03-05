package com.hugqq.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySpelConfig {

    @Bean
    public String name() {
        return "学习Spel表达式";
    }

}
