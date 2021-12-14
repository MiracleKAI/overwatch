package com.miracle.overwatch.common.codec;

import com.miracle.overwatch.common.domain.protocol.Packet;
import com.miracle.overwatch.common.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * @author QiuKai
 * @date 2021/10/4 5:18 下午
 */
public class PacketEncoder extends MessageToMessageEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        byte[] bytes = SerializationUtil.serialize(packet);
        ByteBuf buf = Unpooled.buffer(bytes.length + 32);
        buf.writeInt(bytes.length + 1);
        // 该消息的类型
        buf.writeByte(packet.getType());
        buf.writeBytes(bytes);

        BinaryWebSocketFrame webSocketFrame = new BinaryWebSocketFrame(buf);
        out.add(webSocketFrame);
    }
}
