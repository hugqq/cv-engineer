package com.hugqq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomResponseBodyAdvice  implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 此处可以定义你希望拦截的 Controller 返回类型，比如只拦截某个特定的类或特定的注解
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String servletPath = httpServletRequest.getServletPath();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        log.info("servletPath:{}",servletPath);
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("parameterMap:{}",objectMapper.writeValueAsString(parameterMap));
        // 此处可以对 body 进行包装处理，比如添加统一的返回头信息等
        response.getHeaders().add("Custom-Header", "Custom-Value");
        log.info("body:{}",body);
        return body;
    }
}
