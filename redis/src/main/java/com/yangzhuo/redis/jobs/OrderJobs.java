package com.yangzhuo.redis.jobs;


import com.yangzhuo.redis.consumer.OrderConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class OrderJobs {
    
    @Autowired
    private OrderConsumer orderConsumer;
    
    @Scheduled(cron = "*/1 * * * * ?")
    public void polling() {
        orderConsumer.consumption();
    }
    
    
}
