package com.miracle.overwatch.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * @author QiuKai
 * @date 2021/10/3 4:51 下午
 */
@Slf4j
public class NettyClient {
    private EventLoopGroup group;
    private Channel channel;

    public Channel run(String host, int port) {
        ChannelFuture channelFuture;
        group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientChannelInitializer());

        channelFuture = b.connect(host, port).syncUninterruptibly();
        log.info("host:{}", channelFuture);
        this.channel = channelFuture.channel();
        return this.channel;
    }

    public void destroy(){
        if (null == this.channel) {
            return;
        }
        channel.close();
        group.shutdownGracefully();
    }
}
