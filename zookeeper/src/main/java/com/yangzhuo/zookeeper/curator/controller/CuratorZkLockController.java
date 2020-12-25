package com.yangzhuo.zookeeper.curator.controller;


import com.yangzhuo.zookeeper.curator.lock.service.ZookeeperDsLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CuratorZkLockController {

    @Autowired
    private ZookeeperDsLock zkLock;

    @GetMapping("/test")
    public void test() {
        zkLock.init("/test");
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        zkLock.lock();
                        log.info("加锁成功");
                    } catch (Exception e) {
                        //
                    } finally {
                        zkLock.unlock();
                    }
                }
            }).start();
        }
    }

}
