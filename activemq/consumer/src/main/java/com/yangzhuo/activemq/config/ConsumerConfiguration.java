package com.yangzhuo.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ConsumerConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String host;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory(RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user, password, host);
        factory.setRedeliveryPolicy(redeliveryPolicy);
        factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(true); //是否在每次失败重发时，增长等待时间
        redeliveryPolicy.setMaximumRedeliveryDelay(-1); //设置重大最大拖延时间，-1 表示没有拖延时间，只有setUseExponentialBackOff(true)时生效
        redeliveryPolicy.setMaximumRedeliveries(3); //重发次数
        redeliveryPolicy.setInitialRedeliveryDelay(1); //重发时间间隔
        redeliveryPolicy.setBackOffMultiplier(2); //第一次失败之后重发前等待500毫秒，第二次500*2，依次递增
        redeliveryPolicy.setUseCollisionAvoidance(false); //是否避免消息碰撞
        return redeliveryPolicy;
    }

    @Bean(name = "queueListenerContainerFactory")
    public JmsListenerContainerFactory queueListenerContainerFactory(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode()); //设置手动确认
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean(name = "topicListenerContainerFactory")
    public JmsListenerContainerFactory topicListenerContainerFactory(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());
        factory.setPubSubDomain(true); //设置为发布/订阅模式，默认是queue
        return factory;
    }

}
