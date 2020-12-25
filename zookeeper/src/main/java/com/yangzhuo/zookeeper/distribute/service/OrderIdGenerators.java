package com.yangzhuo.zookeeper.distribute.service;

import com.yangzhuo.zookeeper.distribute.lock.service.ZkLock;
import com.yangzhuo.zookeeper.distribute.lock.service.impl.DistZkLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class OrderIdGenerators implements Runnable{

    private static int count = 0;

    @Value("${zk.url}")
    private String zkConnectUrl;

    private final static String lockName = "order";


    @Override
    public void run() {

        try(
                ZkLock lock = new DistZkLock(zkConnectUrl, lockName, node -> {
                    log.info("释放锁： " + node);
                })
        ) {
            if (lock.lock()) {
                log.info("进入了锁！");
                String id = createId();
                log.info("zk生成Id: " + id);
                Thread.sleep(1_000L);
            }
        } catch (Exception e) {
            log.info("生成id失败");
        }

    }

    public String createId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date()) + "-" + ++count;
    }
}