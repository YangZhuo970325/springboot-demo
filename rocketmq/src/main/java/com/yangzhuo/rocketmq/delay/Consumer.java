package com.yangzhuo.rocketmq.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    
    public static void main(String[] args) throws MQClientException {
        //1.创建消费者Consumer,指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定NameServer地址
        consumer.setNamesrvAddr("192.168.146.147:9876");

        //设置消息消费模式：负载均衡(默认) | 广播模式
        //consumer.setMessageModel(MessageModel.BROADCASTING);

        //3.订阅主体Topic和Tag
        consumer.subscribe("DelayTopic", "*");
        //consumer.setConsumeTimeout(10000);
        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {

                for (MessageExt msg : list) {
                    System.out.println("消息id:[" + msg.getMsgId() + "],队列id：[" + msg.getQueueId() + "],消费消息：" 
                            + new String(msg.getBody()) + ",延迟时间：" + (System.currentTimeMillis() - msg.getStoreTimestamp()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }

        });
        //5.启动消费者Consumer
        consumer.start();
    }
}
