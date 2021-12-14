package com.miracle.overwatch.server.server.handler;

import com.miracle.overwatch.common.domain.ClientInfo;
import com.miracle.overwatch.server.service.impl.ExtServerService;
import com.miracle.overwatch.server.util.CacheUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/11/4 11:48 上午
 */
@Component
@Slf4j
public class ClientInfoHandler extends SimpleChannelInboundHandler<ClientInfo> {

    @Autowired
    private ExtServerService extServerService;

    private static ClientInfoHandler clientInfoHandler;

    @PostConstruct
    private void init(){
        clientInfoHandler = this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientInfo clientInfo) throws Exception {

        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("链接报告开始");
        log.info("链接报告信息：有一客户端链接到本服务端");
        log.info("链接报告IP:{}", channel.remoteAddress().getHostString());
        log.info("链接报告Port:{}", channel.remoteAddress().getPort());
        log.info("连接报告ChannelId:{}", channel.id());
        log.info("链接报告完毕");

        // 将客户端信息添加到redis中
        clientInfo.setChannelId(channel.id().toString());
        CacheUtil.channelMap.put(clientInfo.getClientChannelId(), channel.id().toString());
        clientInfoHandler.extServerService.getRedisUtil().pushObject(clientInfo);
        CacheUtil.usersCache.put(channel.id().toString(), channel);
    }
}
