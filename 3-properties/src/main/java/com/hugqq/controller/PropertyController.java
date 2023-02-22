package com.hugqq.controller;

import cn.hutool.core.lang.Dict;
import com.hugqq.property.ApplicationProperty;
import com.hugqq.property.DeveloperProperty;
import com.hugqq.property.EntityBuilder;
import com.hugqq.property.OtherProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 测试Controller
 * </p>
 */
@RestController
public class PropertyController {
    @Autowired
    private ApplicationProperty applicationProperty;
    @Autowired
    private DeveloperProperty developerProperty;
    @Autowired
    private OtherProperties otherProperties;


    @GetMapping("/property")
    public Dict index() {

        Dict dict = new Dict();
        dict.set("applicationProperty", applicationProperty);
        dict.set("developerProperty", developerProperty);
        dict.set("otherProperties", otherProperties);

        return Dict.create()
                .set("data", dict);
    }
}
