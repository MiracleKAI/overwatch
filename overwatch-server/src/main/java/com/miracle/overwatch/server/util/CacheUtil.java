package com.miracle.overwatch.server.util;


import com.miracle.overwatch.server.mbg.model.ServerInfo;
import com.miracle.overwatch.server.server.NettyServer;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author QiuKai
 * @date 2021/10/3 2:37 下午
 */
public class CacheUtil {

    private CacheUtil(){}

    /**
     * 将channelId 与 Channel 对应，可以通过channelId 找到Channel
     */
    public static Map<String, Channel> usersCache = new ConcurrentHashMap<>();

    /**
     * 客户端的channelId -> 服务端的channelId
     */
    public static Map<String, String> channelMap = new ConcurrentHashMap<>();

    /**
     * 已保存在redis中
     */
    public static Map<String, ServerInfo> serverInfoMap = new ConcurrentHashMap<>();

    public static Map<String, NettyServer> serverMap = new ConcurrentHashMap<>();
}

