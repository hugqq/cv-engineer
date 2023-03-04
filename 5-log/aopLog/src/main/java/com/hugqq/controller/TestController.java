package com.hugqq.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hugqq.annos.Log;
import com.hugqq.enums.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 测试 Controller
 * </p>
 */
@Slf4j
@RestController
public class TestController {

    /**
     * 测试方法
     */
    @GetMapping("/test")
    public Dict test(String str) {
        return Dict.create().set("data", str);
    }

    /**
     * 测试post json方法
     */
    @Log(title = "查询",logType = LogType.SELECT)
    @PostMapping("/testJson")
    public Dict testJson(@RequestBody Map<String, Object> map) {
        final String jsonStr = JSONUtil.toJsonStr(map);
        log.info(jsonStr);
        return Dict.create().set("json", map);
    }
}
