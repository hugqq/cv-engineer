package com.hugqq.utils;

import com.hugqq.config.QLExpressContext;
import com.hugqq.entity.Person;
import com.hugqq.service.QLExpressTestService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;


@Component
public class QlExpressUtil implements ApplicationContextAware {
    private static ExpressRunner runner= new ExpressRunner();


    private ApplicationContext applicationContext;


    public Object execute(String expressString) throws Exception {
        initRunner(runner);
        IExpressContext expressContext = new QLExpressContext(new DefaultContext<>(), applicationContext);
        return runner.execute(expressString, expressContext, new ArrayList<>(), true, false);
    }

    /**
     * @param expressString 执行语句
     * @param context       上下文
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Object execute(String expressString, Map<String, Object> context) throws Exception {
        try {
            initRunner(runner);
            IExpressContext expressContext = new QLExpressContext(context, applicationContext);
            return runner.execute(expressString, expressContext, new ArrayList<>(), true, false);
        } finally {
            runner = new ExpressRunner();
        }
    }

    @SneakyThrows
    private void initRunner(ExpressRunner runner) {
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);
        runner.addOperatorWithAlias("或者", "or", null);
        runner.addOperatorWithAlias("并且", "and", null);
        synchronized (runner) {
            try {
                // 加载自定义函数
                runner.addFunctionOfClassMethod("isOver18", QLExpressTestService.class.getName(), "isOver18",
                        new Class[]{Person.class, int.class}, null);
                runner.addMacro("超过18岁", "isOver18(person,18)");
            } catch (Exception e) {
                throw new RuntimeException("初始化失败表达式", e);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

