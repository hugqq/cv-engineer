package com.hugqq;


import cn.hutool.core.lang.Console;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.dialect.commons.ApacheCommonsLogFactory;
import cn.hutool.log.dialect.console.ConsoleLogFactory;
import cn.hutool.log.dialect.jdk.JdkLogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class SpringbootLogbackApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootLogbackApplication.class, args);
        int length = context.getBeanDefinitionNames().length;
        // Hutool-log对于日志框架的监测顺序是：Slf4j(Logback) > Log4j > Log4j2 > Apache Commons Logging > JDK Logging > Console
        log.trace("Spring boot启动初始化了 {} 个 Bean", length);
        log.debug("Spring boot启动初始化了 {} 个 Bean", length);
        log.info("Spring boot启动初始化了 {} 个 Bean", length);
        log.warn("Spring boot启动初始化了 {} 个 Bean", length);
        log.error("Spring boot启动初始化了 {} 个 Bean", length);

//		// 自动选择日志实现
        Log log = LogFactory.get();
        log.info("This is {} log", "default");
        Console.log("----------------------------------------------------------------------");
//		//自定义日志实现为Apache Commons Logging
        LogFactory.setCurrentLogFactory(new ApacheCommonsLogFactory());
        log.info("This is {} log", "custom apache commons logging");
        Console.log("----------------------------------------------------------------------");
//		//自定义日志实现为JDK Logging
        LogFactory.setCurrentLogFactory(new JdkLogFactory());
        log.info("This is {} log", "custom jdk logging");
        Console.log("----------------------------------------------------------------------");
//		//自定义日志实现为Console Logging
        LogFactory.setCurrentLogFactory(new ConsoleLogFactory());
        log.info("This is {} log", "custom Console");
        Console.log("----------------------------------------------------------------------");
        try {
            int i = 0;
            int j = 1 / i;
        } catch (Exception e) {
            log.error("出错了{},{},{}", "1", "2", "3");

        }

    }


}
