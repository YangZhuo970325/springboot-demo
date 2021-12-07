package com.yangzhuo.redis.delayqueue.impl;

import com.alibaba.fastjson.JSON;
import com.yangzhuo.redis.delayqueue.IDelayQueue;
import com.yangzhuo.redis.dto.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class OrderDelayQueue implements IDelayQueue<Order>, InitializingBean {

    /**
     * 最小分数
     */
    private static final String MIN_SCORE = "0";
    
    private static final String OFFSET = "0";
    
    private static final String LIMIT = "10";

    /**
     * 延迟队列名称
     */
    private static final String ORDER_QUEUE = "ORDER_DELAY_QUEUE";

    /**
     * lua脚本文件名称
     */
    private static final String DEQUEUE_LUA = "dequeue.lua";

    private static final AtomicReference<String> DEQUEUE_LUA_SHA = new AtomicReference<>();
    
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Override
    public void enqueue(Order order) {
        // 60秒之后执行
        String s = String.valueOf(order.getCreateTime().getTime() + 60 * 1000);
        redisTemplate.opsForZSet().add(ORDER_QUEUE, JSON.toJSONString(order), Double.parseDouble(s));
    }

    @Override
    public List<Order> dequeue(String min, String max, String offset, String limit) {
        RedisScript<List> redisScript = RedisScript.of(DEQUEUE_LUA_SHA.get(), List.class);
        List<Object> keys = new ArrayList<>();
        keys.add(ORDER_QUEUE);
        keys.add(min);
        keys.add(max);
        keys.add(offset);
        keys.add(limit);
        List<String> orderList = redisTemplate.execute(redisScript, keys);
        List<Order> result = new ArrayList<>();
        if (!limit.isEmpty()) {
            for (String order : orderList) {
                if (StringUtils.isNotEmpty(order)) {
                    result.add(JSON.parseObject(order, Order.class));
                }
            }
        }
        return result;
    }

    @Override
    public List<Order> dequeue() {
        // 最大分数
        String maxScore = String.valueOf(System.currentTimeMillis());
        return dequeue(MIN_SCORE, maxScore, OFFSET, LIMIT);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassPathResource resource = new ClassPathResource(DEQUEUE_LUA);
        String luaContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        DEQUEUE_LUA_SHA.compareAndSet(null, luaContent);
    }
}
