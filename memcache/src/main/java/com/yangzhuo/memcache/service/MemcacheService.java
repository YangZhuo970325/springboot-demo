package com.yangzhuo.memcache.service;


import com.yangzhuo.memcache.annotation.MemCached;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author: yangzhuo
 * @Date: 2020/12/28 15:26
 */
@Service
public class MemcacheService {

    @MemCached(key = "'getOrderById_'+#orderId", expiration = 5)
    public String getOrderById(String orderId) {
        int num = new Random().nextInt(10);
        return "orderId:" + orderId + ",getOrderById randomId:" + num;
    }

}