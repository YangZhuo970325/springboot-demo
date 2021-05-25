package com.yangzhuo.rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 * 
 * @author yz
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException, MQBrokerException, RemotingException {
        //1. 创建消息生产者producer,并指定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("group2");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.146.147:9876");
        producer.setSendMsgTimeout(10000);
        
        // 设置事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 在改方法中执行本地事务
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (StringUtils.equals("TAGA", message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAGB", message.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equals("TAGC", message.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            /**
             * 该方法是mq进行消息事务状态回查
             * @param messageExt
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("消息的Tag: " + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        
        //3. 启动producer
        producer.start();
        String[] tags = {"TAGA", "TAGB", "TAGC"};
        for (int i = 0; i < 3; i++) {
            //4. 创建消息对象，指定主题topic,tag和消息体
            /**
             * 参数1：消息主体topic
             * 参数2：消息Tag
             * 参数3：消息内容
             */
            Message msg = new Message("TransactionTopic", tags[i], ("Hello World" + i).getBytes());
            // 设置消息回查的时间
            msg.putUserProperty(MessageConst.PROPERTY_CHECK_IMMUNITY_TIME_IN_SECONDS,"5");
            //5. 发送消息
            
            //默认timeout是3000，这里如果使用默认的会报错
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            //发送状态，消息id，接收队列id
            SendStatus status = sendResult.getSendStatus();
            String msgId = sendResult.getMsgId();
            int queueId = sendResult.getMessageQueue().getQueueId();
            System.out.println("发送结果：" + sendResult);
            
            TimeUnit.SECONDS.sleep(1);
        }
        
        //6. 关闭生产者producer
        //producer.shutdown();
        System.out.println("生产者启动");
    }
}
