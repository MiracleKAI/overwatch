package com.miracle.overwatch.server.util.redis;

import com.alibaba.fastjson.JSON;

import com.miracle.overwatch.common.constant.ClientConstant;
import com.miracle.overwatch.common.domain.ClientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要用于共享 接入的client
 * @author QiuKai
 * @date 2021/10/1 3:35 下午
 */
@Service("redisUtil")
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * H 为 hashmap 的名字
     * HK 为key
     * Hv 为value
     * userChannelInfo 以k-v的形式添加到redis的hash(map)中
     * key: "cluster-user"
     * @param clientInfo
     */
    public void pushObject(ClientInfo clientInfo){
        redisTemplate.opsForHash().put(ClientConstant.CLIENT_INFO_KEY, clientInfo.getChannelId(), JSON.toJSONString(clientInfo));
    }


    /**
     * 获取所有用户
     * @return
     */
    public List<ClientInfo> popList(){
        // 获取该hash(map)的所有值
        List<Object> values = redisTemplate.opsForHash().values(ClientConstant.CLIENT_INFO_KEY);

        List<ClientInfo> userChannelInfoList = new ArrayList<>();
        for(Object objStr : values){
            userChannelInfoList.add(JSON.parseObject(objStr.toString(), ClientInfo.class));
        }
        return userChannelInfoList;
    }

    public ClientInfo get(String channelId){
        return (ClientInfo) redisTemplate.opsForHash().get(ClientConstant.CLIENT_INFO_KEY, channelId);

    }
    public void remove(String name){
        redisTemplate.opsForHash().delete(ClientConstant.CLIENT_INFO_KEY, name);
    }

    public void clear(){
        redisTemplate.delete(ClientConstant.CLIENT_INFO_KEY);
    }
}
