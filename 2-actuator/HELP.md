Springboot actuator-api

## 监控接口详解

| HTTP方法 | Endpoint | 描述 |
| --- | :-- | --- |
| GET | /actuator | 查看有哪些endpoint是开放的 |
| GET | /actuator/auditevent | 查看 audit 的事件，例如认证进入，订单失败等 需要搭配 Spring security 使用 |
| GET | /actuator/beans | 查看应用程序中所有 Spring bean 的完整列表，展示了 bean 的类型、单例多例、别名、类的全路径、依赖Jar等内容。 |
| GET | /actuator/conditions | 查看配置在什么条件下有效，或者自动配置为什么无效。 |
| GET | /actuator/configprops | 查看注入带有 @ConfigurationProperties类的 properties 值 |
| GET | /actuator/env (常用) | 查看全部环境属性，可以看到 SpringBoot 加载了哪些 properties，以及这些 properties 的值（但是会自动`*`掉带有 key、password、secret 等关键字的 properties 的值） |
| GET | /actuator/flyway | 查看已应用的任何 Flyway 数据库迁移。                         |
| GET | /actuator/health (常用) | 查看当前 SpringBoot 运行的健康指标，值由 HealthIndicator 的实现类提供（所以可以自定义一些健康指标资讯，加到这里面） |
| GET | /actuator/heapdump | 自动生成Jvm的堆转储文件HeapDump，可以使用监控工具打开此文件查看内存快照。 |
| GET | /actuator/info | 查看 properties 中 info 开头的属性的值，没啥用 |
| GET | /actuator/mappings | 查看全部的 endpoint（包含 Actuator 的），以及他们和 Controller 的关系 |
| GET | /actuator/metrics | 查看有哪些指标可以看（ex: jvm.memory.max、system.cpu.usage），要再使用`/actuator/metrics/{metric.name}`分别查看各指标的详细资讯 |
| GET | /actuator/scheduledtasks | 查看定时任务的资讯 |
| GET | /actuator/threaddump | 展示线程名、线程ID、是否等待锁、线程的状态、线程锁等相关信息。 |
| POST | /actuator/shutdown | 唯一一个需要 POST 请求的 endpoint，优雅关闭SpringBoot |


## 官方文档
https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/