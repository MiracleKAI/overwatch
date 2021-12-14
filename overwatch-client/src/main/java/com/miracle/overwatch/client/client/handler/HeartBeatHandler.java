package com.miracle.overwatch.client.client.handler;


import com.miracle.overwatch.common.domain.HeartBeat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/10/11 3:29 下午
 */
public class HeartBeatHandler extends SimpleChannelInboundHandler<HeartBeat> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeat heartBeat) throws Exception {
        Channel ch = ctx.channel();

    }
}
