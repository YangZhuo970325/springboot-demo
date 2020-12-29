package com.yangzhuo.activemq.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

@Configuration
public class TopicConfig {

    @Value("${topic}")
    private String topic;

    @Bean
    public Topic topic() {
        return new ActiveMQTopic();
    }
}
