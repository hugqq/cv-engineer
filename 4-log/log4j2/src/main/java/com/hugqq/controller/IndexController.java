package com.hugqq.controller;

import com.hugqq.entity.InputMDC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {

    @RequestMapping(value = "/")
    public void index() {
        InputMDC.putMDC();

        log.info("我是一条info日志");

        log.warn("我是一条warn日志");

        log.error("我是一条error日志");

        try {
            int a = 1 / 0;
        } catch (Exception e) {
            log.error("报错了：{}", e.getMessage());
        }
    }

}
