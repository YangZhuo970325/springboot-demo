package com.yangzhuo.rocketmq.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException, MQBrokerException, RemotingException {
        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.146.147:9876");
        //3. 启动producer
        producer.start();

        List<Message> msgs = new ArrayList<Message>();
        //4. 创建消息对象，指定主题topic,tag和消息体
        /**
         * 参数1：消息主体topic
         * 参数2：消息Tag
         * 参数3：消息内容
         */
        Message msg1 = new Message("BatchTopic", "Tag1", ("Hello World 1").getBytes());
        Message msg2 = new Message("BatchTopic", "Tag1", ("Hello World 2").getBytes());
        Message msg3 = new Message("BatchTopic", "Tag1", ("Hello World 3").getBytes());
        
        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);
        
        //5. 发送消息

        //默认timeout是3000，这里如果使用默认的会报错
        SendResult sendResult = producer.send(msgs, 10000);
        //发送状态，消息id，接收队列id
        SendStatus status = sendResult.getSendStatus();
        String msgId = sendResult.getMsgId();
        int queueId = sendResult.getMessageQueue().getQueueId();
        System.out.println("发送结果：" + sendResult);

        TimeUnit.SECONDS.sleep(1);    
        

        //6. 关闭生产者producer
        producer.shutdown();
    }
}
