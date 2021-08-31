package com.yangzhuo.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAnnoController {
    
    // 定义限流资源和限流降级回调函数
    @SentinelResource(value = "Sentinel_Ann", blockHandler = "exceptionHandler")
    @GetMapping("/anno")
    public String hello() {
        return "Hello Sentinel!";
    }
    
    // blockedHandler函数，原方法调用被限流/降级/系统保护的时候调用
    public String exceptionHandler(BlockException e) {
        e.printStackTrace();
        return "系统繁忙，请稍后！";
    }
}
