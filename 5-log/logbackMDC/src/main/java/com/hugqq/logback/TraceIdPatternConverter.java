package com.hugqq.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.hugqq.interceptor.LogHandlerInterceptor;
import org.springframework.util.StringUtils;

public class TraceIdPatternConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        String traceId = LogHandlerInterceptor.getTrack();
        return StringUtils.isEmpty(traceId) ? "NoTraceId" : traceId;
    }
}
