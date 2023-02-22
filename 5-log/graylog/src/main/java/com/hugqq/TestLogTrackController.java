package com.hugqq;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestLogTrackController {

    private static final Logger logger = LoggerFactory.getLogger(TestLogTrackController.class);

    @RequestMapping("/")
    public void logTrack() {

        logger.info("slf4j info测试");
        logger.error("slf4j error测试");
        logger.debug("slf4j debug测试");
        logger.trace("slf4j trace测试");

        log.info("lombok info测试");
        log.error("lombok error测试");
        log.debug("lombok debug测试");
        log.trace("lombok trace测试");
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("出错啦 错误信息：{}", e.getMessage());
        }

    }

}
