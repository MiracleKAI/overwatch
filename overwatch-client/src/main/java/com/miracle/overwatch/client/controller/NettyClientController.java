package com.miracle.overwatch.client.controller;


import com.miracle.overwatch.client.client.NettyClient;
import com.miracle.overwatch.client.client.handler.NettyClientChannelHandler;
import com.miracle.overwatch.common.domain.ClientInfo;

import com.miracle.overwatch.common.api.R;
import com.miracle.overwatch.common.domain.StatusInfo;
import com.miracle.overwatch.common.domain.TimedTask;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/11/3 3:23 下午
 */
@Slf4j
@RestController
@RequestMapping("/client")
public class NettyClientController {

    @PostMapping
    public R startClient(@RequestParam("host")String host, @RequestParam("port") int port){
        ClientInfo clientInfo;
        NettyClient client;
        try {
            log.info("host:{}", host);
            client = new NettyClient();
            final Channel channel = client.run(host, port);
            if (channel == null) {
                return R.error("连接失败");
            }
            String name = UUID.randomUUID().toString();
            clientInfo = new ClientInfo( null, channel.id().toString(), name, host, port, channel.localAddress().toString(), new Date());
            // 需等待websocket建立成功
            NettyClientChannelHandler.handshakeFuture.sync();
            // 发送用户信息给服务器
            channel.writeAndFlush(clientInfo);

            // 准备状态信息
            StatusInfo statusInfo = new StatusInfo();
            statusInfo.setName(name);
            statusInfo.setLocalHost(channel.localAddress().toString());
            statusInfo.setOpen(true);
            statusInfo.setOs(System.getProperty("os.name"));

            // 握手成功后，开启定时任务，定时向服务器发送自身状态
            TimedTask<StatusInfo> timedTask = new TimedTask<>(channel, statusInfo);
            channel.eventLoop().scheduleAtFixedRate(timedTask, 5, 5, TimeUnit.SECONDS);
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok().put("data", clientInfo);
    }
}
