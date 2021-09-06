package com.yangzhuo.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yangzhuo.sentinel.feign.HiRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @Autowired
    private HiRemoteService hiRemoteService;
    
    // 定义资源 value:设置资源的名称 blockHandler:设置限流或降级的处理函数
    @SentinelResource(value = "Sentinel_Springcloud", blockHandler = "exceptionHandler")
    @GetMapping("/anno-springcloud")
    public String hello() {
        // 使用限流规则
        return "Hello Sentinel";
    }
    
    // 被限流或降级的处理函数
    public String exceptionHandler(BlockException e) {
        e.printStackTrace();
        return "系统繁忙，请稍后";
    }
    
    @GetMapping("/feign")
    public String hi() {
        return hiRemoteService.hello();
    }
}
