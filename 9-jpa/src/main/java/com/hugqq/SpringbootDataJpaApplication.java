package com.hugqq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDataJpaApplication.class, args);
    }

}
