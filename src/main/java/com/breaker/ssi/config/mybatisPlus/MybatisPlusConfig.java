package com.breaker.ssi.config.mybatisPlus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: This a config class for mybatis-plus.
 * @author: Breaker93
 * @createTime: 2019/9/21 16:05
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
//    @Bean
//    public IdentifierGenerator idGenerator() {
//        return new CustomIdGenerator();
//    }
}
