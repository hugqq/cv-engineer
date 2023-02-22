package com.hugqq.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Data
@Component
@PropertySource(value = "pro.properties")
@ConfigurationProperties(prefix = "test")
public class OtherProperties   {
    private String name;
    private int age;
}
