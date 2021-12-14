package com.miracle.overwatch.server;

import com.miracle.overwatch.server.util.redis.RedisUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OverwatchServerApplication implements CommandLineRunner {

    private final RedisUtil redisUtil;

    public OverwatchServerApplication(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public static void main(String[] args) {
        SpringApplication.run(OverwatchServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        redisUtil.clear();
    }
}
