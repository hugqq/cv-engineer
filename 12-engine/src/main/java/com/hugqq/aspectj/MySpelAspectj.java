package com.hugqq.aspectj;

import cn.hutool.core.util.StrUtil;
import com.hugqq.annos.MySpelAnno;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 注解切面
 */
@Component
@Slf4j
@Aspect
public class MySpelAspectj {

    ExpressionParser parser = new SpelExpressionParser();
    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @SneakyThrows
    @Around(value = "@annotation(anno)")
    public Object doAfter(ProceedingJoinPoint joinPoint, MySpelAnno anno) {
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);

        }
        // 字符串
        String spelOne = anno.spelOne();
        if (StrUtil.isNotEmpty(spelOne)) {
            Expression spelOneExpression = parser.parseExpression(spelOne);
            String result1 = spelOneExpression.getValue(context, String.class);
            log.info("普通字符串解析的结果 : {}", result1);
        }

        // 运算
        String spelTwo = anno.spelTwo();
        if (StrUtil.isNotEmpty(spelTwo)) {
            Expression spelTwoExpression = parser.parseExpression(spelTwo);
            Boolean result2 = spelTwoExpression.getValue(context, Boolean.class);
            log.info("运算解析的结果 : {}", result2);
        }

        // list
        String spelThree = anno.spelThree();
        if (StrUtil.isNotEmpty(spelThree)) {
            Expression spelThreeExpression = parser.parseExpression(spelThree);
            List result3 = spelThreeExpression.getValue(context, List.class);
            log.info("List解析的结果 : {}", result3);
        }

        // map
        String spelFour = anno.spelFour();
        if (StrUtil.isNotEmpty(spelFour)) {
            Expression spelFourExpression = parser.parseExpression(spelFour);
            Map result = spelFourExpression.getValue(context, Map.class);
            log.info("Map解析的结果  : {}", result);
        }

        String spelFive = anno.spelFive();
        if (StrUtil.isNotEmpty(spelFive)) {

        }
        return joinPoint.proceed();


    }

}
