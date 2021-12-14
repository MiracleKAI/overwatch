package com.miracle.overwatch.client.client;

import com.miracle.overwatch.client.client.handler.NettyClientChannelHandler;
import com.miracle.overwatch.client.client.handler.OrderHandler;
import com.miracle.overwatch.common.codec.PacketDecoder;
import com.miracle.overwatch.common.codec.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author QiuKai
 * @date 2021/10/3 4:55 下午
 */
public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast(new HttpClientCodec());
        p.addLast(new HttpObjectAggregator(65535));
        p.addLast(new PacketDecoder());
        p.addLast(new PacketEncoder());
        p.addLast(new OrderHandler());
        p.addLast(new NettyClientChannelHandler());

    }
}
