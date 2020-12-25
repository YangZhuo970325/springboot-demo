package com.yangzhuo.zookeeper.single.lock.service.impl;

import com.yangzhuo.zookeeper.single.lock.service.Lock;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

@Slf4j
public abstract class AbstractZookeeperLock implements Lock {

    protected static final String LOCK_PATH = "/zookeeper-lock";

    private static final String CONNECTSTR = "127.0.0.1:2181";

    protected ZkClient zkClient = new ZkClient(CONNECTSTR,300000);


    abstract boolean tryLock();

    abstract void waitLock();

    @Override
    public void lock() {
        if(tryLock()) {
            log.info("获取锁成功");
        } else {
            waitLock();
            lock();
        }
    }

    @Override
    public void unLock() {
        if(zkClient.exists(LOCK_PATH)) {
            zkClient.delete(LOCK_PATH);
            System.out.println("释放锁");
        }
    }
}
