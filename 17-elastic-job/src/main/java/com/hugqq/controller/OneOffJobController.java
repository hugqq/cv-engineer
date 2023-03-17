package com.hugqq.controller;

import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.OneOffJobBootstrap;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Lazy
@RestController
public class OneOffJobController {


    @Resource(name = "MyOccurErrorNoticeEmailJob")
    private OneOffJobBootstrap myOccurErrorNoticeEmailJob;

    @GetMapping("/myOccurErrorNoticeEmailJob")
    public ResponseEntity<String> executeOneOffJob() {
        myOccurErrorNoticeEmailJob.execute();
        return ResponseEntity.ok().build();
    }

}
