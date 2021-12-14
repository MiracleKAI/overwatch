package com.miracle.overwatch.common.domain.protocol;


import com.miracle.overwatch.common.domain.ClientInfo;
import com.miracle.overwatch.common.domain.HeartBeat;
import com.miracle.overwatch.common.domain.OperatorOrder;
import com.miracle.overwatch.common.domain.StatusInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author QiuKai
 * @date 2021/10/2 8:41 下午
 */
public class PacketClassMap {
    private PacketClassMap() {}

    public static final Map<Byte, Class<? extends Packet>> PACKET_CLASS_MAP = new ConcurrentHashMap<>();

    static {
        PACKET_CLASS_MAP.put(MsgType.OPERATOR_ORDER, OperatorOrder.class);
        PACKET_CLASS_MAP.put(MsgType.STATUS_INFO, StatusInfo.class);
        PACKET_CLASS_MAP.put(MsgType.HEART_BEAT, HeartBeat.class);
        PACKET_CLASS_MAP.put(MsgType.CLIENT_INFO, ClientInfo.class);
    }
}
