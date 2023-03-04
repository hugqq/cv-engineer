package com.hugqq.controller;

import com.hugqq.annos.MySpelAnno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @MySpelAnno(spelOne = "#a",spelTwo = "#b > #c")
    @GetMapping("/test")
    private void  test(String a,Integer b,Integer c){
        log.info("{},{},{}",a,b,c);
    }

}
