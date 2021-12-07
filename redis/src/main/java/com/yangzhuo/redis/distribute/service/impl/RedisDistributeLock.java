package com.yangzhuo.redis.distribute.service.impl;

import com.yangzhuo.redis.distribute.service.DistributeLock;
import org.redisson.client.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributeLock implements DistributeLock {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    String uuid = UUID.randomUUID().toString();
    private static final String script_unlock = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    @Override
    public boolean getLock(String lockId) {

        return redisTemplate.opsForValue().setIfAbsent(lockId, uuid, 100, TimeUnit.SECONDS);
    }

    @Override
    public boolean releaseLock(String lockId) {

        List<String> keys = new ArrayList<>();
        keys.add(lockId);

        //指定ReturnType为Long.class
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script_unlock, Long.class);

        Long result = redisTemplate.execute(redisScript, keys, uuid);
        return lockId.equals(result);
    }
}
