package com.yangzhuo.activemq.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

@Component
@EnableScheduling
public class Producer {

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Scheduled(fixedDelay = 5000)
    public void sendQueue() {
        jmsTemplate.convertAndSend(queue, "测试P2P消息队列" + System.currentTimeMillis());
    }

    @Scheduled(fixedDelay = 5000)
    public void sendTopic() {
        jmsTemplate.convertAndSend(queue, "测试TOPIC消息队列" + System.currentTimeMillis());
    }


}
