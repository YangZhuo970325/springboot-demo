package com.yangzhuo.zookeeper.single.service;

import com.yangzhuo.zookeeper.single.lock.service.Lock;
import com.yangzhuo.zookeeper.single.lock.service.impl.ZookeeperDistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class OrderIdGenerator implements Runnable{

    private static int count = 0;

    @Override
    public void run() {

        Lock lock = new ZookeeperDistributeLock();
        try{
            lock.lock();
            String id = createId();
            log.info("zk生成Id: " + id);
        } catch (Exception e) {
            log.info("生成id失败");
        } finally {
            log.info("zk释放锁");
            lock.unLock();
        }

    }

    public String createId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date()) + "-" + ++count;
    }
}