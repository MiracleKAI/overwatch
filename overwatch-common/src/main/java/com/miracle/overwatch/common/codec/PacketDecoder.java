package com.miracle.overwatch.common.codec;

import com.miracle.overwatch.common.domain.protocol.Packet;
import com.miracle.overwatch.common.domain.protocol.PacketClassMap;
import com.miracle.overwatch.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * 收到WebSocketFrame 转为Packet类
 *
 * @author QiuKai
 * @date 2021/10/4 5:21 下午
 */
public class PacketDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = msg.content();

        if (byteBuf.readableBytes() < 4) {
            return;
        }

        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        if (length > byteBuf.readableBytes()) {
            byteBuf.resetReaderIndex();
            return;
        }

        // 获取Packet类型
        Byte command = byteBuf.readByte();
        Class<? extends Packet> aClass = PacketClassMap.PACKET_CLASS_MAP.get(command);

        byte[] packetData = new byte[length - 1];
        byteBuf.readBytes(packetData);
        Packet packet = SerializationUtil.deserialize(packetData, aClass);
        out.add(packet);
    }
}

