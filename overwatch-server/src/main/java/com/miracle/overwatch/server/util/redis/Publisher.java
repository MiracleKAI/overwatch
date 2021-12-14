package com.miracle.overwatch.server.util.redis;



import com.miracle.overwatch.common.domain.protocol.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author QiuKai
 * @date 2021/10/1 3:30 下午
 */
@Service
public class Publisher {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public Publisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void pushPacket(String topic, Packet packet){
        // 对msgAgreement 按设置的规则进行序列化，并将其发布到topAic这个频道
        redisTemplate.convertAndSend(topic, packet);
    }
}
