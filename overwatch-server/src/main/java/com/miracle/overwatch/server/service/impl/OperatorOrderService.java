package com.miracle.overwatch.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.miracle.overwatch.common.domain.OperatorOrder;


import com.miracle.overwatch.server.util.CacheUtil;
import com.miracle.overwatch.server.util.redis.Publisher;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author QiuKai
 * @date 2021/10/4 11:31 上午
 */
@Service
@Slf4j
public class OperatorOrderService {


    private final ExtServerService extServerService;
    private final Publisher publisher;

    public OperatorOrderService(ExtServerService extServerService, Publisher publisher) {
        this.extServerService = extServerService;
        this.publisher = publisher;
    }

    public boolean sentOrder(OperatorOrder operatorOrder){
        log.info("order:{}", JSON.toJSONString(operatorOrder));
        String channelId = operatorOrder.getChannelId();
        // 是否在本机的缓存中, 为空则不在
        if (channelId == null) {
            publisher.pushPacket("cluster-push-packet", operatorOrder);
            return true;
        }
        Channel channel = CacheUtil.usersCache.get(channelId);
        // 直接转发
        channel.writeAndFlush(operatorOrder);
        return true;
    }
}
