package com.yangzhuo.redis.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class PubSubController {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @GetMapping("/publish")
    public void publish(@RequestParam(value = "message") String message) {
        redisTemplate.convertAndSend("news", message);
    }
    
    
    class MyRedisChannelListener implements MessageListener {
        @Override
        public void onMessage(Message message, byte[] bytes) {
            byte[] channel = message.getChannel();
            byte[] body = message.getBody();
            
            try {
                String content = new String(body, "UTF-8");
                String address = new String(channel, "UTF-8");
                System.out.println("get " + content + " from " + address);
                
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new MyRedisChannelListener());
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // news 频道名称
        container.addMessageListener(listenerAdapter, new PatternTopic("news"));
        return container;
    }
}
