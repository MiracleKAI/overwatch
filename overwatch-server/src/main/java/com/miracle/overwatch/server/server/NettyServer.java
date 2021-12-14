package com.miracle.overwatch.server.server;


import com.miracle.overwatch.server.service.impl.ExtServerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

import java.util.concurrent.Callable;

/**
 * @author QiuKai
 * @date 2021/10/3 1:33 下午
 */
@Slf4j
public class NettyServer implements Callable<Channel> {

    private final ExtServerService extServerService;
    private final InetSocketAddress address;

    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    public NettyServer(ExtServerService extServerService, InetSocketAddress address) {
        this.extServerService = extServerService;
        this.address = address;
    }

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new NettyServerChannelInitializer(extServerService));

            channelFuture = serverBootstrap.bind(this.address).syncUninterruptibly();
            this.channel = channelFuture.channel();
        }finally {
            if(null != channelFuture && channelFuture.isSuccess()){
                log.info("==========服务器启动成功===========");

            }else {
                log.info("==========服务器启动失败===========");
            }
        }
        return this.channel;
    }

    public void destroy() {
        if (null == channel) {
            return;
        }
        channel.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }

}
