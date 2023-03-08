package com.hugqq.aspectj;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.hugqq.entity.LogBO;
import com.hugqq.service.LogService;
import com.hugqq.utils.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * 方法切面
 */
@Aspect
@Component
@Slf4j
public class AopLog {

    /**
     * 切入点  切点表达式  idea匹配到了会有个红点跳转
     */
    @Pointcut("execution(public * com.hugqq.controller.*Controller.*(..))")
    public void log() {

    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        final LogBO l = LogBO.builder()
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .ip(IpUtils.getIp(request))
                .url(request.getRequestURL().toString())
                .classMethod(String.format("%s.%s", point.getSignature().getDeclaringTypeName(), point.getSignature().getName()))
                .httpMethod(request.getMethod())
                .requestParams(getNameAndValue(point))
                .result(result)
                .timeCost(System.currentTimeMillis() - startTime + "ms")
                .userAgent(header)
                .browser(userAgent.getBrowser().toString())
                .os(userAgent.getOperatingSystem().toString()).build();
        log.info("全局拦截 : {}", JSONUtil.toJsonStr(l));
        CompletableFuture.supplyAsync(() -> LogService.insert(l));
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
