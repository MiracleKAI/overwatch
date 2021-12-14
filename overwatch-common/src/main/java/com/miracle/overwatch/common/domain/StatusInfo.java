package com.miracle.overwatch.common.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.miracle.overwatch.common.domain.protocol.MsgType;
import com.miracle.overwatch.common.domain.protocol.Packet;
import com.miracle.overwatch.common.domain.protocol.Task;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author QiuKai
 * @date 2021/10/5 5:12 下午
 */
@Setter
@Getter
@Slf4j
@TableName("status_info")
public class StatusInfo extends Packet implements Task {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String localHost;
    private Boolean open;
    private String os;
    private Date timeStamp;

    @Override
    public Byte getType() {
        return MsgType.STATUS_INFO;
    }

    @Override
    public void executeTask(Channel channel) {
        this.setTimeStamp(new Date());
        log.info("status: {}", JSON.toJSON(this));
        channel.writeAndFlush(this);
    }
}
