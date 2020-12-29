package com.yangzhuo.memcache.controller;


import com.whalin.MemCached.MemCachedClient;
import com.yangzhuo.memcache.service.MemcacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: yangzhuo
 * @Date: 2020/12/28 14:29
 */
@RestController
@Slf4j
public class MemcacheController {

    @Autowired
    private MemCachedClient memCachedClient;

    @Autowired
    private MemcacheService memcacheService;

    @GetMapping(value = "memcache1/{orderId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String memcache1(@PathVariable String orderId) throws InterruptedException {
        memCachedClient.set("orderId", orderId + System.currentTimeMillis());
        Object value = memCachedClient.get("orderId");
        log.info("first OrderId from memcache:" + value);

        memCachedClient.set("orderId", "afterOrderId" + System.currentTimeMillis(), new Date(2000));
        value = memCachedClient.get("orderId");
        log.info("after OrderId from memcache:" + value);

        Thread.sleep(2000);

        value = memCachedClient.get("orderId");
        log.info("finally OrderId from memcache:" + value);

        return "success";
    }

    @GetMapping(value = "memcache2/{orderId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String memcache2(@PathVariable String orderId) {
        String result = memcacheService.getOrderById(orderId);
        log.info("print result:{}", result);
        return result;
    }

}