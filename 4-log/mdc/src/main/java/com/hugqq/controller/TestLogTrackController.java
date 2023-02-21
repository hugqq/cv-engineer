package com.hugqq.controller;


import com.hugqq.thread.TrackRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class TestLogTrackController {

    private static final Logger logger = LoggerFactory.getLogger(TestLogTrackController.class);

    @RequestMapping("/logTrack")
    public void logTrack() {
        logger.info("测试");
    }

    @RequestMapping("/asynLogTrack")
    public void asynLogTrack() {
        logger.info("ces--------");
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("ces");
            }
        }).start();
    }

    @RequestMapping("/asynLogTrackHasTrace")
    public void asynLogTrackHasTrace() {
        logger.info("ces1------");
        new Thread(new TrackRunnable() {
            @Override
            public void trackRun() {
                logger.info("ces2-----------");
            }
        }).start();
    }


    @RequestMapping("/asynLogTrackHasTrace1")
    public void asynLogTrackHasTrace1() {
        logger.info("ces1------");
        new Thread(new TrackRunnable() {
            @Override
            public void trackRun() {
                logger.info("ces2-----------");
                LogUtil.sout1();
            }
        }).start();
        CompletableFuture.runAsync(new TrackRunnable() {
            @Override
            public void trackRun() {
                LogUtil.sout2();
                logger.info("ces5-----------");
            }
        });
        try {
            int a =  1/0;
        } catch (ArithmeticException e) {
            logger.error("ces6  -----------   {}",e.getMessage());
        }
    }
}
