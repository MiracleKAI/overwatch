package com.miracle.overwatch.server.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author QiuKai
 * @date 2021/10/5 5:01 下午
 */
public class IdleHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        // 心跳
//        if(evt instanceof IdleStateEvent){
//            IdleStateEvent event = (IdleStateEvent)evt;
//            String eventType = null;
//            switch (event.state()){
//                case READER_IDLE:
//                    eventType = "读空闲";
//                    break;
//                case WRITER_IDLE:
//                    eventType = "写空闲";
//                    break;
//                case ALL_IDLE:
//                    eventType = "读写空闲";
//                    break;
//                default:
//                    break;
//            }
//            System.out.println(ctx.channel().remoteAddress() + "---超时时间---" + eventType);
//            System.out.println("服务器做出相应处理。。。");
//        }
    }
}
