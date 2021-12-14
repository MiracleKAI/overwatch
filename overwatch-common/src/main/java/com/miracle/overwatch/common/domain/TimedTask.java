package com.miracle.overwatch.common.domain;


import com.miracle.overwatch.common.domain.protocol.Task;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;


/**
 * @author QiuKai
 * @date 2021/10/5 5:37 下午
 */
@Slf4j
public class TimedTask<T extends Task> implements Runnable{
    private final Channel channel;
    private final T task;

    public TimedTask(Channel channel, T task) {
        this.channel = channel;
        this.task = task;
    }

    @Override
    public void run() {

        log.info("开始执行定时任务");
        task.executeTask(this.channel);
        log.info("定时任务执行结束");
    }
}
