package com.yangzhuo.mybatisplus.config;


import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sun.misc.PerformanceLogger;

@MapperScan("com.yangzhuo.mybatisplus")
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {
    
    //注册乐观锁插件
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }
    
    //分页插件
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return paginationInnerInterceptor();
    }
    
    
}
