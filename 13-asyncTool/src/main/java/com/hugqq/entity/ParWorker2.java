package com.hugqq.entity;

import com.jd.platform.async.callback.ICallback;
import com.jd.platform.async.callback.IWorker;
import com.jd.platform.async.worker.WorkResult;
import com.jd.platform.async.wrapper.WorkerWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ParWorker2 implements IWorker<String, String>, ICallback<String, String> {


    @SneakyThrows
    @Override
    public String action(String object, Map<String, WorkerWrapper> map) {
        long start = System.currentTimeMillis();
        log.info("================入参：{}================", object);
        Thread.sleep(3000);
        long end =System.currentTimeMillis();
        log.info("================ParWorker2运行时长：{}================", end - start);
        return "parWorker2";
    }

    @Override
    public String defaultValue() {
        return "default ParWorker2";
    }

    @Override
    public void begin() {
        System.out.println(Thread.currentThread().getName() + "-- ParWorker2 Start --" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean success, String param, WorkResult<String> workResult) {
        if (success) {
            log.info("ParWorker2成功 参数是：{} 结果是：{}",param,workResult.getResult());
        } else {
            log.info("ParWorker2失败 参数是：{} 结果是：{}",param,workResult.getResult());
        }
    }

}