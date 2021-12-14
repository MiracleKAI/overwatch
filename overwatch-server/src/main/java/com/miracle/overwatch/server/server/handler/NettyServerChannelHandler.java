package com.miracle.overwatch.server.server.handler;


import com.miracle.overwatch.common.domain.ClientInfo;
import com.miracle.overwatch.server.service.impl.ExtServerService;
import com.miracle.overwatch.server.util.CacheUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * @author QiuKai
 * @date 2021/10/3 2:36 下午
 */
@Slf4j
public class NettyServerChannelHandler extends ChannelInboundHandlerAdapter {

    private final ExtServerService extServerService;

    /**
     * 用于统一使用WebSocket的 decoder 和 encoder
     * 可接管webSocketFrame的处理
     */
    private WebSocketServerHandshaker webSocketServerHandshaker;

    public NettyServerChannelHandler(ExtServerService extServerService) {
        this.extServerService = extServerService;
    }

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开链接:{}", ctx.channel().localAddress().toString());
        extServerService.getRedisUtil().remove(ctx.channel().id().toString());
        CacheUtil.usersCache.remove(ctx.channel().id().toString(), ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 用于握手
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            handlerHttpRequest(request, ctx);
            return;
        }
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
            handlerWebSocketFrame(webSocketFrame, ctx);
        }

    }

    private void handlerWebSocketFrame(WebSocketFrame webSocketFrame, ChannelHandlerContext ctx) throws Exception {
        // 连接关闭消息帧
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), ((CloseWebSocketFrame) webSocketFrame).retain());
        }
    }


    private void handlerHttpRequest(FullHttpRequest request, ChannelHandlerContext ctx) {
        // 解析失败
        if (!request.decoderResult().isSuccess()) {
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            if (response.status().code() != 200) {
                ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), StandardCharsets.UTF_8);
                response.content().writeBytes(buf);
                buf.release();
            }
            // 如果是非Keep-Alive，关闭连接
            ChannelFuture f = ctx.channel().writeAndFlush(response);
            if (response.status().code() != 200) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
            return;
        }
        // 因为WebSocket具有多个版本，所以需要Factory根据浏览器支持的版本来生成对应的WebSocketServerHandShaker
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel().localAddress() + "/web_socket", null, true);
        webSocketServerHandshaker = factory.newHandshaker(request);
        if (null == webSocketServerHandshaker) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 会对消息进行编解码
            webSocketServerHandshaker.handshake(ctx.channel(), request);
        }
    }
}
