package com.yangzhuo.redis.delayqueue;

import java.util.List;

public interface IDelayQueue<T> {

    /**
     * 入队
     * @param message
     */
    void enqueue(T message);

    /**
     * 出队
     * @param min 分数区间：最小分数
     * @param max 分数区间：最大分数
     * @param offset
     * @param limit 
     * @return
     */
    List<T> dequeue(String min, String max, String offset, String limit);

    /**
     * 出队
     * @return
     */
    List<T> dequeue();
}
