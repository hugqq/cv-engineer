package com.hugqq.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <p>
 * 任务工厂
 * </p>
 */
@Slf4j
@RestController
public class TaskFactoryDemoController {
    @Autowired
    private TaskFactory task;

    /**
     * 测试异步任务 5s
     *
     * @throws InterruptedException
     */
    @GetMapping("/asyncTaskTest")
    public void asyncTaskTest() throws InterruptedException, ExecutionException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("异步任务");
        Future<Boolean> asyncTask1 = task.asyncTask1();
        Future<Boolean> asyncTask2 = task.asyncTask2();
        Future<Boolean> asyncTask3 = task.asyncTask3();
        // 调用 get() 阻塞主线程
        asyncTask1.get();
        asyncTask2.get();
        asyncTask3.get();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("异步任务全部执行结束，总耗时：{} 秒", stopWatch.getTotalTimeSeconds());
    }

    /**
     * 测试同步任务 10s
     *
     * @throws InterruptedException
     */
    @GetMapping("/taskTest")
    public void taskTest() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("同步任务");
        task.task1();
        task.task2();
        task.task3();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("同步任务全部执行结束，总耗时：{} 秒", stopWatch.getTotalTimeSeconds());
    }

}
