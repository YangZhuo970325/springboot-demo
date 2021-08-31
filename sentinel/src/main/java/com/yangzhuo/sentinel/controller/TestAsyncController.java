package com.yangzhuo.sentinel.controller;

import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yangzhuo.sentinel.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAsyncController {
    
    @Autowired
    private AsyncService asyncService;
    
    @GetMapping("/async")
    public void hello() {

        AsyncEntry asyncEntry = null;
        //使用限流规则
        try {
            // 定义资源，限流的入口
            asyncEntry = SphU.asyncEntry("Sentinel_Async");
            //调用异步方法，被保护的资源
            asyncService.hello();
        } catch (BlockException e) {
            e.printStackTrace();
            //被限流或降级的处理
            System.out.println("系统繁忙，请稍后"); 
        } finally {
            if (asyncEntry != null) {
                // 限流的出口
                asyncEntry.exit();
            }
        }
    }
}
