package com.hugqq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHelloApplication.class, args);
    }

        @RequestMapping("/")
        public String initial() {
            return "hello world";
        }

}
