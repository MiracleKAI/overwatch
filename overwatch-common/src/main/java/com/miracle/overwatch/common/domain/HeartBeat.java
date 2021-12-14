package com.miracle.overwatch.common.domain;


import com.miracle.overwatch.common.domain.protocol.MsgType;
import com.miracle.overwatch.common.domain.protocol.Packet;
import com.miracle.overwatch.common.domain.protocol.Task;
import io.netty.channel.Channel;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/10/11 3:26 下午
 */
public class HeartBeat extends Packet implements Task {

    @Override
    public Byte getType() {
        return MsgType.HEART_BEAT;
    }

    @Override
    public void executeTask(Channel channel) {

    }
}
