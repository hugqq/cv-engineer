package com.hugqq.entity;

import com.hugqq.utils.NetUtil;
import org.jboss.logging.NDC;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 通过在log4j.properties中的 appender使用的ConversionPattern中使用%x选项，可以在日志消息中显示NDC的内容
 * 与Log4j一样,Log4j2配置文件log4j2.xml中使用%x选项
 */
@Component
public class InputNDC implements EnvironmentAware {

    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        InputNDC.environment = environment;
    }

    public static void putNdc() {
      NDC.push("hostName="+NetUtil.getLocalHostName());
            NDC.push("ip="+NetUtil.getLocalIp());
            NDC.push("applicationName="+environment.getProperty("spring.application.name"));

    }

    public static void popNdc() {
        NDC.pop();
        NDC.pop();
        NDC.pop();
        NDC.clear();
    }

}
