package com.yangzhuo.rocketmq.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

public class OneWayProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException, MQBrokerException, RemotingException {
        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.146.147:9876");
        producer.setSendMsgTimeout(10000);
        //3. 启动producer
        producer.start();
        for (int i = 0; i < 10; i++) {
            //4. 创建消息对象，指定主题topic,tag和消息体
            /**
             * 参数1：消息主体topic
             * 参数2：消息Tag
             * 参数3：消息内容
             */
            Message msg = new Message("base", "Tag3", ("Hello World,单向消息" + i).getBytes());
            //5. 发送消息
            
            producer.sendOneway(msg);
            

            TimeUnit.SECONDS.sleep(1);
        }

        //6. 关闭生产者producer
        producer.shutdown();
    }
}
