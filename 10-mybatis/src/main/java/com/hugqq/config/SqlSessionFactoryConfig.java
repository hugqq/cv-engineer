package com.hugqq.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author wulincheng
 * @Date 2020年6月23日18:25:22
 * 创建SQL连接工厂类
 */
@Configuration
public class SqlSessionFactoryConfig {
    @javax.annotation.Resource
    DataSource dataSource;


    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);//更多参数请自行注入
        bean.setPlugins(new MybatisInterceptor());
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/*.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();
    }
}
