package com.hugqq.job;

import com.hugqq.dao.FooRepository;
import com.hugqq.entity.Foo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class MyDataflowJob implements DataflowJob<Foo> {


    @Resource
    private FooRepository fooRepository;

    @Override
    public List<Foo> fetchData(ShardingContext shardingContext) {
        log.info("分片: {} | 参数: {} | 时间: {} | 线程: {} | {}",
                shardingContext.getShardingItem(),shardingContext.getShardingParameter(), LocalDateTime.now(), Thread.currentThread().getId(), "DATAFLOW FETCH");
        return fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
    }


    @Override
    public void processData(ShardingContext shardingContext, List<Foo> list) {
        log.info("分片: {} | 参数: {} | 时间: {} | 线程: {} | {}",
                shardingContext.getShardingItem(),shardingContext.getShardingParameter(), LocalDateTime.now(), Thread.currentThread().getId(), "DATAFLOW PROCESS");
        for (Foo each : list) {
            fooRepository.setCompleted(each.getId());
        }
    }
}
