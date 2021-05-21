package com.yangzhuo.rocketmq.delay;

import com.yangzhuo.rocketmq.order.OrderStep;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException, MQBrokerException, RemotingException {
        //1. 创建消息生产者producer,并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2. 指定NameServer地址
        producer.setNamesrvAddr("192.168.146.147:9876");
        //3. 启动producer
        producer.start();
        producer.setSendMsgTimeout(10000);

        //构建消息集合
        List<OrderStep> orderStepList = OrderStep.buildOrders();

        //发送消息
        for (int i = 0; i < orderStepList.size(); i++) {
            String body = orderStepList.get(i).toString();
            Message message = new Message("DelayTopic", "Order", "i = " + i, body.getBytes());
            message.setDelayTimeLevel(3);
            /**
             * 参数一：消息对象
             * 参数二：消息队列的选择器
             * 参数三：选择的业务标识
             */
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                /**
                 * @param list : 队列集合
                 * @param message : 消息对象
                 * @param o : 业务标识的参数
                 */
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long orderId = (long) o;
                    long index = orderId % list.size();
                    return list.get((int) index);
                }
            }, orderStepList.get(i).getOrderId());

            System.out.println("发送结果：" + sendResult);
        }

        //6. 关闭生产者producer
        producer.shutdown();
    }
}
