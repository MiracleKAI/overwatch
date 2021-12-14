package com.miracle.overwatch.server.util.redis.config;

import com.miracle.overwatch.server.util.redis.PacketReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author QiuKai
 * @date 2021/10/1 2:42 下午
 */
@Configuration
public class ReceiverConfig {
    /**
     * 用于消息监听
     * @param connectionFactory
     * @param messageListenerAdapter 设置监听器
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(connectionFactory);
        // 设置监听器
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic("cluster-push-packet"));
        return redisMessageListenerContainer;
    }

    /**
     * 当消息来时会调用receiveMessage方法
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter packetListenerAdapter(PacketReceiver receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
