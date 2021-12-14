package com.miracle.overwatch.common.domain.protocol;

import io.netty.channel.Channel;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/10/11 3:34 下午
 */
public interface Task {

    /**
     * 执行任务
     */
    void executeTask(Channel channel);
}
