package com.hugqq.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.hugqq.*.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 新的分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件 如果是不同类型的库，请不要指定DbType，其会自动判断。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 防全表更新与删除插件
//        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁插件 在实体类的字段上加上@Version注解
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 动态表名插件
//        interceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor());
        return interceptor;
    }


}
