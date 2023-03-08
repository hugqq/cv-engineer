package com.hugqq.aspectj;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.hugqq.annos.Log;
import com.hugqq.entity.LogBO;
import com.hugqq.utils.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 注解切面
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @SneakyThrows
    @Around(value = "@annotation(methodAnnotationALog)")
    public Object doAfter(ProceedingJoinPoint joinPoint, Log methodAnnotationALog) {
        // 方法注解
        if (methodAnnotationALog == null) {
            return null;
        }
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
        String header = request.getHeader("User-Agent");
        Object result = joinPoint.proceed();
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        // 设置方法名称
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String requestParams = className + "." + methodName + "()";
        final LogBO l = LogBO.builder()
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .ip(IpUtils.getIp(request))
                .url(request.getRequestURL().toString())
                .classMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()))
                .httpMethod(request.getMethod())
                .requestParams(getNameAndValue(joinPoint))
                .timeCost(System.currentTimeMillis() - startTime + "ms")
                .userAgent(header)
                .result(result)
                .title(methodAnnotationALog.title())
                .logType(methodAnnotationALog.logType())
                .browser(userAgent.getBrowser().toString())
                .os(userAgent.getOperatingSystem().toString()).build();
        log.info("注解拦截 : {}", JSONUtil.toJsonStr(l));
        return result;
    }


    /**
     * 获取方法参数名和参数值
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();
        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }
}
