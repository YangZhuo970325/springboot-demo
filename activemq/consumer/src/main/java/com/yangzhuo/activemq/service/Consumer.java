package com.yangzhuo.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @JmsListener(destination = "${queue}", containerFactory = "queueListenerContainerFactory")
    public void receiveQueue(String msg) {
        log.info("从ActiveMQ queue收到消息：" + msg);
    }

    @JmsListener(destination = "${topic}", containerFactory = "topicListenerContainerFactory")
    public void receiveTopic(String msg) {
        log.info("从ActiveMQ topic收到消息：" + msg);
    }
}
