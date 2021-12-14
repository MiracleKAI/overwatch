package com.miracle.overwatch.server.service.impl;

import com.miracle.overwatch.server.util.redis.RedisUtil;
import org.springframework.stereotype.Service;

/**
 * @author QiuKai
 * @date 2021/10/5 2:10 下午
 */
@Service
public class ExtServerService {

    private final RedisUtil redisUtil;

    public ExtServerService(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }
}
