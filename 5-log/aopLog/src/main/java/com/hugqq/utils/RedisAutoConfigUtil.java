package com.hugqq.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RedisAutoConfigUtil {

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    @SneakyThrows
    public static String getParams(HttpServletRequest request) {
        String params = request.getQueryString();
        if (StrUtil.isBlank(params)) {
            params = jsonParams(request);
            if (StrUtil.isBlank(params)) {
                params = formParams(request);
            }
        }
        return params;
    }

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    public static String getKey(HttpServletRequest request,String params) {
        String method = request.getMethod();
        StringBuffer requestURL = request.getRequestURL();
        return "redisAutoGenerateKey:" + SecureUtil.md5(method + requestURL + params);
    }


    @SneakyThrows
    public static String formParams(HttpServletRequest request) {
        String params;
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> paramMaps = new HashMap<>(16);
        for (String s : paramMap.keySet()) {
            String[] value = paramMap.get(s);
            if (value.length == 1) {
                paramMaps.put(s, value[0]);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        params = mapper.writeValueAsString(paramMaps);
        return params;
    }

    @SneakyThrows
    public static String jsonParams(HttpServletRequest request) {
        return IOUtils.toString(request.getInputStream(), "UTF-8");
    }



}

