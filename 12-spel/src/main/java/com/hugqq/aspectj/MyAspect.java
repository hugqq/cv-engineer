package com.hugqq.aspectj;

import com.hugqq.annos.MySpelAnno;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 注解切面
 */
@Aspect
@Component
@Slf4j
public class MyAspect {

    ExpressionParser parser = new SpelExpressionParser();
    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @SneakyThrows
    @Around(value = "@annotation(anno)")
    public Object doAfter(ProceedingJoinPoint joinPoint,MySpelAnno anno) {
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        String spelOne = anno.spelOne();
        Expression spelOneExpression = parser.parseExpression(spelOne);
        String result1 = spelOneExpression.getValue(context, String.class);
        log.info("result1 : {}", result1);
        String spelTwo = anno.spelTwo();
        Expression spelTwoExpression = parser.parseExpression(spelTwo);
        Boolean result2 = spelTwoExpression.getValue(context, Boolean.class);
        log.info("result2 : {}", result2);
        return joinPoint.proceed();


    }

}
