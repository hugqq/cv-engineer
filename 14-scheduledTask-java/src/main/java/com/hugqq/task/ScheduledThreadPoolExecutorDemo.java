package com.hugqq.task;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(false).build());
        executorService.scheduleAtFixedRate(() -> {
            int i = 1;
            System.out.println(Thread.currentThread().getName() + " ----> " + i);
            i++;
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
}
