package com.yangzhuo.redis.controller;

import com.yangzhuo.redis.delayqueue.IDelayQueue;
import com.yangzhuo.redis.distribute.service.DistributeLock;
import com.yangzhuo.redis.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RedisController {
    
    @Autowired
    private IDelayQueue delayQueue;

    @Autowired
    private DistributeLock distributeLock;

    @Resource
    private RedissonClient redissonClient;

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

    @GetMapping("/lockByRedisson")
    public void lockByRedisson(int productId) {
        RLock lock = redissonClient.getLock("stock:" + productId);
        try {
            lock.lock(20, TimeUnit.SECONDS);

            // 处理业务逻辑
            Thread.sleep(10000);
            log.info("处理业务逻辑");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
