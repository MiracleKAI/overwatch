package com.miracle.overwatch.server.util.redis;

/**
 * @author QiuKai
 * @date 2021/10/1 3:12 下午
 */
public abstract class AbstractReceiver {

    /**
     * 接受消息
     * @param message
     */
    public abstract void receiveMessage(Object message);
}
