package com.miracle.overwatch.server.util.redis.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis 发布配置，只有订阅某个频道，才能收到某个频道的消息
 * @author QiuKai
 * @date 2021/10/1 2:42 下午
 */
@Configuration
public class PublisherConfig {

    /**
     * 设置redisTemplate 会自动注入到其他地方
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisMessageTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(new FastJsonRedisSerializer<>(Object.class));
        return redisTemplate;
    }
}
