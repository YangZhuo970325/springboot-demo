package com.yangzhuo.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableAsync //开启异步调用支持
public class SentinelApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(SentinelApplication.class);
    }
}
