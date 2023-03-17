package com.hugqq.job.base;

import org.quartz.*;

/**
 * <p>
 * Job 基类，主要是在 {@link org.quartz.Job} 上再封装一层，只让我们自己项目里的Job去实现
 * </p>
 */
public interface BaseJob extends Job {

    @Override
    void execute(JobExecutionContext context) throws JobExecutionException;
}
