package com.hugqq.config;

import com.hugqq.entity.InterceptAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

@Slf4j
@Component
/**
 * @Intercepts：标识该类是一个拦截器
 * @Signature：指明自定义拦截器需要拦截哪一个类型，哪一个方法；
 * type：对应四种类型中的一种；
 * method：对应接口中的哪类方法（因为可能存在重载方法）；
 * args：对应哪一个方法
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})}
)
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 先判断是不是SELECT操作 不是直接过滤
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        if (SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
        }

        if (SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
        }
        //id为执行的mapper方法的全路径名
        String id = mappedStatement.getId();
        log.info("拦截到当前请求方法的全路径名为 ---> {}", id);
        //sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        //获取到原始sql语句
        String sql = boundSql.getSql();
        String resultSql = sql;

        //获取参数
        Object parameter = statementHandler.getParameterHandler().getParameterObject();
        log.info("拦截到当前请求SQL为 ---> {}", sql);
        log.info("请求类型为 ---> {} ", sqlCommandType);
        log.info("拦截到当前请求参数为 ---> {}", parameter);

        //注解逻辑判断
        int index = mappedStatement.getId().lastIndexOf(".");
        Class<?> classType = Class.forName(mappedStatement.getId().substring(0, index));
        String mName = mappedStatement.getId().substring(index + 1);
        Method[] declaredMethods = classType.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (mName.equals(method.getName()) && method.isAnnotationPresent(InterceptAnnotation.class)) {
                InterceptAnnotation interceptorAnnotation = method.getAnnotation(InterceptAnnotation.class);
                if (interceptorAnnotation.flag()) {
                    resultSql = sql + " limit " + interceptorAnnotation.value();
                    log.info("resultSql: {}", resultSql);
                }
            }
        }

        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, resultSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
