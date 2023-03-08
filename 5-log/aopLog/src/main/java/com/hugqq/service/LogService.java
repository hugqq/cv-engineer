package com.hugqq.service;

import com.hugqq.entity.LogBO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogService {
    public static boolean insert(LogBO log) {
//        Console.log("异步调用入库 : {}", JSONUtil.toJsonStr(log));
        return true;
    }
}
