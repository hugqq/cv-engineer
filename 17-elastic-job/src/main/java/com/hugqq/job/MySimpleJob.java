package com.hugqq.job;

import com.hugqq.dao.FooRepository;
import com.hugqq.entity.Foo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@Slf4j
public class MySimpleJob implements SimpleJob {


    @Autowired
    private FooRepository fooRepository;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("分片: {} | 参数: {} |时间: {} | 线程: {} | {}",
                shardingContext.getShardingItem(), shardingContext.getShardingParameter(), LocalDateTime.now(), Thread.currentThread().getId(), "SIMPLE EXECUTE");
        List<Foo> data = fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }
}
