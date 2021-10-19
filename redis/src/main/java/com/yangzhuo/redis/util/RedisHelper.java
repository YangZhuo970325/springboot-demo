package com.yangzhuo.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisHelper {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 设置redis string:value 值
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 2, TimeUnit.HOURS);
    }

    /**
     * 删除redis string:value 值
     * @param key
     */
    public void delString(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 获取redis string类型的value值
     * @param key
     * @return
     */
    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置redis hash:value 值
     * @param key 
     * @param hashKey 
     * @param value
     */
    public void setHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取redis hash值
     * @param key
     * @param hashKey
     * @return
     */
    public String getHash(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }
}
