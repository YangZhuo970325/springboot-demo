package com.yangzhuo.zookeeper.curator.lock.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ZookeeperDsLock {

    private InterProcessMutex mutex;

    @Autowired
    private CuratorFramework client;

    public void init(String code) {
        this.mutex = new InterProcessMutex(client, code);
    }

    public boolean lock() {
        try {
            this.mutex.acquire();
            log.info(Thread.currentThread().getName() + "加锁成功");
            return true;
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + "加锁失败");
        }
        return false;
    }

    public void unlock() {
        try {
            this.mutex.release();
            log.info(Thread.currentThread().getName() + "锁释放成功");
        } catch (Exception e) {
            log.error(Thread.currentThread().getName() + "锁释放失败");
        }
    }

}