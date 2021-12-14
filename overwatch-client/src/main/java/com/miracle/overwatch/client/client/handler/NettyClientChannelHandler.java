package com.miracle.overwatch.client.client.handler;


import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author QiuKai
 * @date 2021/10/3 4:52 下午
 */
@Slf4j
public class NettyClientChannelHandler extends ChannelInboundHandlerAdapter {

    private WebSocketClientHandshaker handShaker;

    public static ChannelPromise handshakeFuture;

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws URISyntaxException, InterruptedException {

        String uri = "ws:/" + ctx.channel().remoteAddress() + "/web_socket";
        handShaker = WebSocketClientHandshakerFactory.newHandshaker(
                new URI(uri), WebSocketVersion.V13, null, true, new DefaultHttpHeaders());
        handShaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("WebSocket Client disconnected!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpResponse) {
            handlerHttpRequest(ctx, (FullHttpResponse) msg);
        }
    }

    private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpResponse response) {
        Channel ch = ctx.channel();

        if (!handShaker.isHandshakeComplete()) {
            try {
                handShaker.finishHandshake(ch, response);
                handshakeFuture.setSuccess();
                System.out.println("WebSocket Client connected!");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("=======这里是客户端======="));

            } catch (WebSocketHandshakeException e) {
                System.out.println("WebSocket Client failed to connect");
                handshakeFuture.setFailure(e);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
