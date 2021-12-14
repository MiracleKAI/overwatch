package com.miracle.overwatch.client.client.handler;

import com.alibaba.fastjson.JSON;
import com.miracle.overwatch.common.domain.OperatorOrder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * @author QiuKai
 * @date 2021/10/4 4:24 下午
 */
@Slf4j
public class OrderHandler extends SimpleChannelInboundHandler<OperatorOrder> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OperatorOrder msg) throws Exception {
        log.info("收到命令：{}", JSON.toJSONString(msg));
        //TODO 根据不同的控制指令类型，做出相应的操作
    }
}
