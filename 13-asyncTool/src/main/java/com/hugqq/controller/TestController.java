package com.hugqq.controller;

import com.hugqq.entity.*;
import com.jd.platform.async.executor.Async;
import com.jd.platform.async.wrapper.WorkerWrapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ParWorker1 w1;
    @Autowired
    private ParWorker2 w2;
    @Autowired
    private ParWorker3 w3;
    @Autowired
    private ResultWorker resultWorker;

    @SneakyThrows
    @RequestMapping("/parallelTest1")
    public void parallelTest1() {
        ParWorker1 w1 = new ParWorker1();
        ParWorker2 w2 = new ParWorker2();
        ParWorker3 w3 = new ParWorker3();
        ResultVo resultVo = new ResultVo();
        WorkerWrapper<String, String> wrapper1 = new WorkerWrapper.Builder<String, String>()
                .worker(w1)
                .param("1")
//                .next(resultwrapper)
                .callback(w1)
                .id("one")
                .build();
        WorkerWrapper<String, String> wrapper2 = new WorkerWrapper.Builder<String, String>()
                .worker(w2)
                .param("2")
//                .depend(resultwrapper)
//                .next(wrapper1)
                .callback(w2)
                .id("two")
                .build();
        WorkerWrapper<String, String> wrapper3 = new WorkerWrapper.Builder<String, String>()
                .worker(w3)
                .param("3")
//                .depend(resultwrapper)
//                .next(wrapper2)
                .callback(w3)
                .id("third")
                .build();
        WorkerWrapper<ResultVo, ResultVo> resultwrapper = new WorkerWrapper.Builder<ResultVo, ResultVo>()
                .worker(resultWorker)
                .param(resultVo)
                .callback(resultWorker)
                .depend(wrapper1, wrapper2, wrapper3)
                .id("four")
                .build();
        Async.beginWork(200000, wrapper1, wrapper2, wrapper3);
//        Thread.sleep(10000);
        System.out.println("===========================================");
        System.out.println(resultwrapper.getWorkResult().getResult());
//        Async.shutDown();
    }


}
