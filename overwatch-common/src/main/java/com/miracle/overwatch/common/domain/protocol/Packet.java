package com.miracle.overwatch.common.domain.protocol;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author QiuKai
 * @date 2021/10/2 8:34 下午
 */
@Data
public abstract class Packet {
    private String channelId;
    public Packet(){}

    /**
     * 获取消息类型
     * @return
     */
    public abstract Byte getType();
}
