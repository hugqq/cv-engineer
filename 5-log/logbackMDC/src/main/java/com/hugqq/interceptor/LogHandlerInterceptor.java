package com.hugqq.interceptor;

import cn.hutool.core.util.IdUtil;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class LogHandlerInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<String> TRACK_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
          从其他系统约定好传递header头中的某个参数过来当统一的链路，没有则自己生成
         */
        String trackId = Optional.ofNullable(request.getHeader("trackId")).orElse(IdUtil.simpleUUID());
        TRACK_THREAD_LOCAL.set(trackId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TRACK_THREAD_LOCAL.remove();
    }

    public static String getTrack() {
        return TRACK_THREAD_LOCAL.get() == null ? "init" : TRACK_THREAD_LOCAL.get();
    }

    public static void setTrace(String trace) {
        TRACK_THREAD_LOCAL.set(trace);
    }

    public static void remove() {
        TRACK_THREAD_LOCAL.remove();
    }
}

