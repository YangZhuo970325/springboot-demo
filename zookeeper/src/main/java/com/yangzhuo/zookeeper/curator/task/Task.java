package com.yangzhuo.zookeeper.curator.task;

import com.yangzhuo.zookeeper.curator.lock.annotation.ZookeeperDistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class Task {

    @Value("${server.port}")
    private String port;

    //@Scheduled(cron = "0/5 * * * * ?")
    @ZookeeperDistributeLock()
    public void zkLock() throws InterruptedException {
        Thread.sleep(8000);
        log.info("zookeeper task start:" + port);
    }

}