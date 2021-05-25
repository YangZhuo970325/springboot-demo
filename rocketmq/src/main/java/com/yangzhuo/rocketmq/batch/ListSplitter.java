package com.yangzhuo.rocketmq.batch;

import org.apache.rocketmq.common.message.Message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 消息分割器
 */
public class ListSplitter implements Iterator<List<Message>> {
    
    //每条消息最大4M
    private final int SIZE_LIMIT = 1024 * 1024 *4;
    
    private final List<Message> messages;
    
    private int currentIndex;
    
    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        int nextIndex = currentIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            tmpSize = tmpSize + 20; //增加日志的开销20字节
            if (tmpSize > SIZE_LIMIT) {
                //单个消息超过了最大的限制
                //忽略，否则会阻塞分割的进程
                if (nextIndex - currentIndex == 0) {
                    //加入下一个子列表没有元素，则添加这个子列表然后退出循环，否则只是退出循环
                    nextIndex++;
                }
                break;
            }
            if (tmpSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += tmpSize;
            }
        }
        List<Message> subList = messages.subList(currentIndex, nextIndex);
        currentIndex = nextIndex;
        return subList;
    }
    
    /** 使用
     * 
     * 
    ListSplitter splitter = new ListSplitter(messages);
    while (splitter.hasNext()) {
        try {
            List<Message> listItem = splitter.next();
            producer.send(listItem);
        } catch (Exception e) {
            e.printStackTrace();
            //处理异常
        }
    }
     **/
}

