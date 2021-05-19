package com.yangzhuo.rocketmq.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 *
 * @author yz
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("GROUP1");
        //2. 指定NameServer地址
        defaultMQProducer.setNamesrvAddr("192.168.146.147:9876");
        //3. 启动producer
        defaultMQProducer.start();
        //4. 创建消息对象，指定主题topic,tag和消息体
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("base", "Tag2", ("Hello World" + i).getBytes());
            //5. 发送异步消息
            defaultMQProducer.send(msg, new SendCallback() {
                /**
                 * 发送成功回调函数
                 * @param sendResult
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送结果：" + sendResult);
                }

                /**
                 * 发送失败回调函数
                 * @param throwable
                 */
                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送异常：" + throwable);
                }
            }, 10000);
            TimeUnit.SECONDS.sleep(1);
        }
        
        //6. 关闭生产者producer
        defaultMQProducer.shutdown();
        
    }
}
