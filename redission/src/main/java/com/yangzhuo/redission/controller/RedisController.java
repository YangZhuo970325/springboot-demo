package com.yangzhuo.redission.controller;

import com.yangzhuo.redission.dto.Animal;
import com.yangzhuo.redission.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
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
}
