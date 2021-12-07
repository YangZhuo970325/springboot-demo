package com.yangzhuo.redis.distribute.service;

public interface DistributeLock {

    /**
     * 尝试加锁
     * @param lockId
     * @return
     */
    boolean getLock(String lockId);


    /**
     *
     * @param lockId
     */
    boolean releaseLock(String lockId);
}
