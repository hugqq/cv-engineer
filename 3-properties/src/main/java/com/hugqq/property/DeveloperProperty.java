package com.hugqq.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 开发人员配置信息
 * </p>
 */
@Data
@ConfigurationProperties(prefix = "developer")
@Component
public class DeveloperProperty {
    private String name;
    private String website;
    private String qq;
    private String phoneNumber;
}
