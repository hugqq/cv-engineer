package com.hugqq.job;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyHashedWheelTimerJob {


    public static void main(String[] args) {
//        simpleHashedWheelTimer();
        reScheduleHashedWheelTimer();
    }

    public static void simpleHashedWheelTimer() {
        log.info("init task 1...");
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 8);
        // add a new timeout
        timer.newTimeout(timeout -> {
            log.info("running task 1...");
        }, 5, TimeUnit.SECONDS);
    }

    @SneakyThrows
    public static void reScheduleHashedWheelTimer() {
        log.info("init task 2...");

        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 8);

        Thread.sleep(5000);

        // add a new timeout
        Timeout tm = timer.newTimeout(timeout -> {
            log.info("running task 2...");
        }, 5, TimeUnit.SECONDS);

        // cancel
        if (!tm.isExpired()) {
            log.info("cancel task 2...");
            tm.cancel();
        }

        // reschedule
        timer.newTimeout(tm.task(), 3, TimeUnit.SECONDS);
    }

}
