package com.yangzhuo.redis.consumer;

import com.google.common.collect.Lists;
import com.yangzhuo.redis.delayqueue.IDelayQueue;
import com.yangzhuo.redis.dto.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class OrderConsumer {

    @Autowired
    private IDelayQueue<Order> iDelayQueue;
    
    public void consumption() {
        boolean result = false;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Order> messages = iDelayQueue.dequeue();

        if (!messages.isEmpty()) {
            result = true;
            log.info("订单消息处理定时任务开始执行......");
            // 集合等分放到线程池中执行
            List<List<Order>> partition = Lists.partition(messages, 2);
            int size = partition.size();
            final CountDownLatch latch = new CountDownLatch(size);
            for (List<Order> p : partition) {
                new OrderConsumer.ConsumeTask(p, latch);
            }
            try {
                latch.await();
            } catch (InterruptedException ignore) {
                log.error("InterruptedException====>>", ignore);
            }
        }
        if (result) {
            stopWatch.stop();
            log.info("订单消息处理定时任务执行完毕,耗时:{} ms......", stopWatch.getTime());
        }
    }
    
    @RequiredArgsConstructor
    private static class ConsumeTask implements Runnable {
        
        private final List<Order> orderList;
        
        private final CountDownLatch latch;

        @Override
        public void run() {
            try {
                for (Order order : orderList) {
                    try {
                        log.info("延迟处理成功!订单信息:{}", order);
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                latch.countDown();
            }
        }
    }
}
