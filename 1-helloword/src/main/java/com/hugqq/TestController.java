package com.hugqq;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {
    @RequestMapping("/")
    public String initial() {
        return "hello world";
    }

    @GetMapping("favicon.ico")
    public void returnNoFavicon() {
    }

    @RequestMapping("/{name}")
    public String initial(@PathVariable("name") String name, String age) {
        return String.format("姓名: %s 年龄: %s", name, age);
    }

}
