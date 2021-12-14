package com.miracle.overwatch.server.server;


import com.miracle.overwatch.common.codec.PacketDecoder;
import com.miracle.overwatch.common.codec.PacketEncoder;

import com.miracle.overwatch.server.server.handler.ClientInfoHandler;
import com.miracle.overwatch.server.service.impl.ExtServerService;
import com.miracle.overwatch.server.server.handler.IdleHandler;
import com.miracle.overwatch.server.server.handler.NettyServerChannelHandler;
import com.miracle.overwatch.server.server.handler.StatusInfoHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author QiuKai
 * @date 2021/10/3 1:37 下午
 */

public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ExtServerService extServerService;

    public NettyServerChannelInitializer(ExtServerService extServerService) {
        this.extServerService = extServerService;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 用于对httpObject对象进行编解码
        pipeline.addLast("http-codec", new HttpServerCodec());
        // 该对象可将这些请求整合
        pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
        // 以块的方式读写
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast(new PacketDecoder());
        pipeline.addLast(new PacketEncoder());
        /*
         * 处理空闲状态的处理器
         * readerIdleTime 表示多长时间没有读，会发送一个心跳检测包，检测是否连接
         * writerIdleTime 表示多长时间没有写，会发送一个心跳检测包，
         * allIdleTime 表示多长时间没有读和写
         * 会触发相应的事件，交由pipeline上下一个handler处理
         * 通过回调下一个handler的 userEventTriggered()， 在该方法中处理
         */
        pipeline.addLast(new StatusInfoHandler());
        pipeline.addLast(new ClientInfoHandler());
        pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
        pipeline.addLast(new IdleHandler());
        // 加入一个对空闲检测进一步处理的handler(自定义)
        pipeline.addLast(new NettyServerChannelHandler(extServerService));
    }
}
