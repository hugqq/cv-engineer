package com.hugqq.entity;

import com.jd.platform.async.callback.ICallback;
import com.jd.platform.async.callback.IWorker;
import com.jd.platform.async.worker.WorkResult;
import com.jd.platform.async.wrapper.WorkerWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ResultWorker implements IWorker<ResultVo, ResultVo>, ICallback<ResultVo, ResultVo> {


    @Override
    public void begin() {
        System.out.println(Thread.currentThread().getName() + "-- ResultWorker start --" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean b, ResultVo resultVo, WorkResult<ResultVo> workResult) {
//        System.out.println("最终结果是：" + workResult.getResult());
    }

    @Override
    public ResultVo action(ResultVo resultVo, Map<String, WorkerWrapper> map) {
        log.info("-----------------");
        log.info("ParWorker1查询结果是： " + map.get("one").getWorkResult());
        log.info("ParWorker2查询结果是： " + map.get("two").getWorkResult());
        log.info("ParWorker3查询结果是： " + map.get("third").getWorkResult());
        log.info("-----------------");
        String r1 = (String) map.get("one").getWorkResult().getResult();
        String r2 = (String) map.get("two").getWorkResult().getResult();
        String r3 = (String) map.get("third").getWorkResult().getResult();
        resultVo.setR1(r1);
        resultVo.setR2(r2);
        resultVo.setR3(r3);
        return resultVo;
    }

    @Override
    public ResultVo defaultValue() {
        return new ResultVo();
    }
}
