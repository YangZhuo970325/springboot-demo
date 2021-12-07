package com.yangzhuo.redission.controller;

import com.yangzhuo.redission.dto.Animal;
import com.yangzhuo.redission.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RedisController {
    
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;
    
    @GetMapping("/put")
    public void put() {
        Animal animal = new Animal();
        animal.setAId("21");
        animal.setAName("小白");
        animal.setColor("red");
        animal.setHobby("play");

        //HashMap<String, Animal> map = new HashMap<>();
        //map.put("kkk", animal);

        redisTemplate.opsForHash().put("animal", "klkk", SerializeUtil.serialize(animal));
    }

    @GetMapping("/lockByRedisson")
    public void lockByRedisson(int productId) {
        RLock lock = redissonClient.getLock("stock:" + productId);
        try {
            lock.lock(20, TimeUnit.SECONDS);
            // 可重入
            lock.lock(20, TimeUnit.SECONDS);
            lock.lock(20, TimeUnit.SECONDS);

            // 处理业务逻辑
            Thread.sleep(10000);
            log.info("处理业务逻辑");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
            lock.unlock();
        }
    }
}
