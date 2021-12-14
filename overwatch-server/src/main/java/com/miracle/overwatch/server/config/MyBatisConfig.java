package com.miracle.overwatch.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * @author QiuKai
 * @date 2021/8/12 9:36 下午
 */
@Configuration
@MapperScan({"com.miracle.overwatch.server.mbg.mapper", "com.miracle.overwatch.server.dao"})
public class MyBatisConfig {
}
