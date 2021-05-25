package com.yangzhuo.rocketmq.filter.sql;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //1.创建消费者Consumer,指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定NameServer地址
        consumer.setNamesrvAddr("192.168.146.147:9876");
        
        //设置消息消费模式：负载均衡(默认) | 广播模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        
        //3.订阅主体Topic和Tag
        //在rocketmq，服务端，需要在broker.conf中加上开启property过滤的配置：enablePropertyFilter=true
        consumer.subscribe("FilterTagTopic", MessageSelector.bySql("i > 5"));
        //consumer.setConsumeTimeout(10000);
        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            
            //接收消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动消费者Consumer
        consumer.start();
        System.out.println("消费者启动");
    }
}
