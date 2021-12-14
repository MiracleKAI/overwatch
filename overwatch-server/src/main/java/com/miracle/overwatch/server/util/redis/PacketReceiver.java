package com.miracle.overwatch.server.util.redis;


import com.miracle.overwatch.common.domain.protocol.Packet;
import com.miracle.overwatch.common.util.MsgUtil;

import com.miracle.overwatch.server.util.CacheUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author QiuKai
 * @date 2021/10/1 3:14 下午
 */
@Service
@Slf4j
public class PacketReceiver extends AbstractReceiver{


    @Override
    public void receiveMessage(Object message) {
        log.info("接收到push的消息：{}", message);
        System.out.println(message.toString());
        // 从消息中读取channelId
        Packet packet = MsgUtil.json2Obj(message.toString());
        // 该消息已经被另一台服务器所接收，该userCache是另一台服务器的cache
        Channel channel = CacheUtil.usersCache.get(packet.getChannelId());
        if(null == channel){
            return;
        }
        // 转发给消息中channelId对应的channel，只需传Packet
        channel.writeAndFlush(packet);
    }
}
