package com.yangzhuo.redis.controller;

import com.yangzhuo.redis.delayqueue.IDelayQueue;
import com.yangzhuo.redis.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RedisController {
    
    @Autowired
    private IDelayQueue delayQueue;

    @GetMapping("/enqueue")
    public void enqueue(@RequestParam(value = "orderId") Long orderId,
                        @RequestParam(value = "userName") String userName) {
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setId(orderId);
        order.setUserName(userName);
        
        delayQueue.enqueue(order);
    }
}
