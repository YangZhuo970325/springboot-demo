package com.yangzhuo.memcache.until;

import com.whalin.MemCached.MemCachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: yangzhuo
 * @Date: 2020/12/28 14:57
 */
@Component
public class MemcachedUtil {
    /*
    set 用于将value（数据值）存储到指定的key（键）中。如果该key已经存在，
    则会用新的value值替换旧value值，也就是可以实现更新的操作。

    add 用于将value存储在指定的key中，若key存在，则不会更新数据。
    若key存在，set命令会做更新操作；而add命令则不会更新数据，即之前值保持不变，并得到响应：NOT_STORED.

    replace 用于替换已存在的key的value值，若key不存在，则替换失败，并得到响应：NOT_STORED。
     */

    @Autowired
    private MemCachedClient cachedClient;

    public boolean add(String key, Object value) {
        return cachedClient.add(key, value);
    }

    public boolean add(String key, Object value, Date date) {
        return cachedClient.add(key, value, date);
    }

    public boolean set(String key, Object value) {
        return cachedClient.set(key, value);
    }

    public boolean set(String key, Object value, Date expire) {
        return cachedClient.set(key, value, expire);
    }

    public boolean replace(String key, Object value) {
        return cachedClient.replace(key, value);
    }

    public boolean replace(String key, Object value, Date expire) {
        return cachedClient.replace(key, value, expire);
    }

    public Object get(String key) {
        return cachedClient.get(key);
    }

}