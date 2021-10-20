package com.yangzhuo.redis;

import com.yangzhuo.redis.util.RedisHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    
    @Autowired
    private RedisHelper redisHelper;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Test
    public void setString() {
        redisHelper.setString("name", "yz");
    }

    @Test
    public void getString() {
        String name = redisHelper.getString("name");
        System.out.println(name);
    }
    
    @Test
    public void delString() {
        redisHelper.delString("name");
    }
    
    @Test
    public void setHash() {
        redisHelper.setHash("person:1", "name", "yz");
        redisHelper.setHash("person:1", "age", "24");
        redisHelper.setHash("person:1", "sex", "male");

        redisHelper.setHash("person:2", "name", "qq");
        redisHelper.setHash("person:2", "age", "20");
        redisHelper.setHash("person:2", "sex", "female");
    }
    
    @Test
    public void getHash() {
        String name = redisHelper.getHash("person:1", "name");
        System.out.println(name);
    }
    
    
    @Test
    public void setList() {
        redisTemplate.opsForList().leftPush("name", "yz");
        redisTemplate.opsForList().leftPush("name", "qq");
    }
    
    @Test
    public void getyList() {
        String name1 = (String) redisTemplate.opsForList().rightPop("name");
        String name2 = (String) redisTemplate.opsForList().rightPop("name");
        System.out.println("name1: " + name1 +", name2: " + name2);
    }
    
    @Test
    public void setSet() {
        redisTemplate.opsForSet().add("yz:friends", "qq");
        redisTemplate.opsForSet().add("yz:friends", "aa");
        redisTemplate.opsForSet().add("yz:friends", "bb");
        redisTemplate.opsForSet().add("yz:friends", "cc");

        redisTemplate.opsForSet().add("qq:friends", "bb");
        redisTemplate.opsForSet().add("qq:friends", "dd");
        redisTemplate.opsForSet().add("qq:friends", "kk");
        redisTemplate.opsForSet().add("qq:friends", "cc");
        
        Set<String> set = redisTemplate.opsForSet().intersect("yz:friends", "qq:friends");

        for (String friend : set) {
            System.out.println("common friend: " + friend);
        }
    }
    
    
    @Test
    public void setZset() {
        redisTemplate.opsForZSet().add("jobs", "coding", 5);
        redisTemplate.opsForZSet().add("jobs", "sleeping", 1);
        redisTemplate.opsForZSet().add("jobs", "talking", 3);
        redisTemplate.opsForZSet().add("jobs", "eating", 4);

        System.out.println("时间执行顺序为：");
        
        Set<String> jobs = redisTemplate.opsForZSet().range("jobs", 0, 1);

        for (String job : jobs) {
            System.out.println(job);
        }
    }
    
    @Test
    public void hyperLogLogSet() {
        redisTemplate.opsForHyperLogLog().add("logintime", "2021-10-17 00:00:00");
        redisTemplate.opsForHyperLogLog().add("logintime", "2021-10-17 10:00:00");
        redisTemplate.opsForHyperLogLog().add("logintime", "2021-10-17 10:00:00");
        Long loginTime = redisTemplate.opsForHyperLogLog().size("logintime");
        System.out.println("loginTime:" + loginTime);
    }
    
    @Test
    public void pubTest() {
        redisTemplate.convertAndSend("news", "你好");
    }
    
    
    
    
}
