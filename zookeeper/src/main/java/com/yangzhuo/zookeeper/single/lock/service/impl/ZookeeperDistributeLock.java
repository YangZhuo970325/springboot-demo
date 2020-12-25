package com.yangzhuo.zookeeper.single.lock.service.impl;

import org.I0Itec.zkclient.IZkDataListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class ZookeeperDistributeLock extends AbstractZookeeperLock {

    private CountDownLatch latch;

    @Override
    boolean tryLock() {
        try {
            zkClient.createEphemeral(LOCK_PATH);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    void waitLock() {
        zkClient.subscribeDataChanges(LOCK_PATH, listener);
        if (zkClient.exists(LOCK_PATH)) {
            latch = new CountDownLatch(1);
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //删除事件通知
        zkClient.unsubscribeDataChanges(LOCK_PATH, listener);
    }

    IZkDataListener listener = new IZkDataListener() {
        //节点改变的事件通知
        @Override
        public void handleDataChange(String s, Object o) throws Exception {
        }

        //节点被删除
        @Override
        public void handleDataDeleted(String s) throws Exception {
            //当节点被删除了唤醒 去重新获取锁
            if (latch != null) {
                latch.countDown();
            }
        }
    };
}
