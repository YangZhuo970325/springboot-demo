package com.yangzhuo.redission;

import com.yangzhuo.redission.dto.Animal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest {
    
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        Animal animal = new Animal();
        animal.setAId("21");
        animal.setAName("小白");
        animal.setColor("red");
        animal.setHobby("play");

        //HashMap<String, Animal> map = new HashMap<>();
        //map.put("kkk", animal);

        redisTemplate.opsForHash().put("animal", "klkk", "qweqweqw");
    }
}
