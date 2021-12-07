package com.yangzhuo.redis.controller;

import com.yangzhuo.redis.delayqueue.IDelayQueue;
import com.yangzhuo.redis.distribute.service.DistributeLock;
import com.yangzhuo.redis.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class RedisController {
    
    @Autowired
    private IDelayQueue delayQueue;

    @Autowired
    private DistributeLock distributeLock;

    @GetMapping("/enqueue")
    public void enqueue(@RequestParam(value = "orderId") Long orderId,
                        @RequestParam(value = "userName") String userName) {
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setId(orderId);
        order.setUserName(userName);
        
        delayQueue.enqueue(order);
    }

    @GetMapping("/lock")
    public void lock() {
        distributeLock.getLock("123");
    }

    @GetMapping("/unlock")
    public void unlock() {
        distributeLock.releaseLock("123");
    }
}
