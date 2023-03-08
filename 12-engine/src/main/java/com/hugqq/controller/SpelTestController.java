package com.hugqq.controller;

import com.hugqq.annos.MySpelAnno;
import com.hugqq.entity.MySpelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SpelTestController {

    @Autowired
    private MySpelEntity mySpelEntity;


    @MySpelAnno(spelOne = "#a")
    @GetMapping("/test1")
    public void test1(String a) {
    }

    @MySpelAnno(spelOne = "'1,2,3'.concat(',4,5,6')")
    @GetMapping("/test2")
    public void test2() {
    }


    @MySpelAnno(spelTwo = "#b EQ #c")
    @GetMapping("/test3")
    public void test3(String b, String c) {
    }


    @GetMapping("/test4")
    @MySpelAnno(spelThree = "{1,2,3}")
    public void test4() {
    }


    @GetMapping("/test5")
    @MySpelAnno(spelFour = "{'name':'张三','age':18}")
    public void test5() {
    }

    @GetMapping("/test6")
    @MySpelAnno(spelThree = "{{'name':'张三','age':18},{'name':'李四','age':19},{'name':'王五','age':20}}")
    public void test6() {
    }

}
