package com.miracle.overwatch.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miracle.overwatch.common.api.R;

import com.miracle.overwatch.common.api.ResultCode;
import com.miracle.overwatch.common.constant.ServerConstants;
import com.miracle.overwatch.common.domain.TimedTask;
import com.miracle.overwatch.common.util.HttpUtils;
import com.miracle.overwatch.common.util.NetUtil;

import com.miracle.overwatch.server.dto.HeartBeatReg;
import com.miracle.overwatch.server.dto.RegisterInfo;
import com.miracle.overwatch.server.service.impl.ExtServerService;
import com.miracle.overwatch.server.mbg.model.ServerInfo;
import com.miracle.overwatch.server.server.NettyServer;
import com.miracle.overwatch.server.util.CacheUtil;

import io.netty.channel.Channel;
import io.netty.channel.ServerChannel;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author QiuKai
 * @date 2021/10/4 3:04 下午
 */
@Slf4j
@RestController
@RequestMapping("/server")
public class NettyController {

    /**
     * 线程池，保存多个NettyServer
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final ExtServerService extServerService;

    @Value("${register.ip}")
    private String registerIp;
    @Value("${register.port}")
    private String registerPort;

    private NettyServer nettyServer;

    private final StringRedisTemplate redisTemplate;

    public NettyController(ExtServerService extServerService, StringRedisTemplate redisTemplate) {
        this.extServerService = extServerService;
        this.redisTemplate = redisTemplate;
    }


    @GetMapping
    public R openNettyServer() {
        try {
            int port = NetUtil.getPort();
            log.info("启动Netty服务，获取可用端口：{}", port);

            nettyServer = new NettyServer(extServerService, new InetSocketAddress(NetUtil.getHost(), port));
            // 将nettyServer 提交给线程池管理
            Future<Channel> future = executorService.submit(nettyServer);
            Channel channel = future.get();
            if (null == channel) {
                throw new RuntimeException("netty server open error channel is null");
            }
            while (!channel.isActive()) {
                log.info("启动Netty服务，循环等待启动...");
                Thread.sleep(500);
            }

            // 在注册中心注册
            RegisterInfo registerInfo = new RegisterInfo(101, NetUtil.getHost() + ":" + port + "/overwatch/connect", "Get", "connect", "overwatch", 40, "Netty 服务器");
            log.info("registerInfo:{}", registerInfo);
            final Response response = HttpUtils.doPost("http://" + registerIp + ":" + registerPort + "/push", JSON.toJSONString(registerInfo));

            if (response != null && response.isSuccessful()) {

                if (ResultCode.SUCCESS.getCode() != response.code()) {
                    response.close();
                    return R.error("服务注册失败");
                }
                response.close();
            } else if (response != null) {
                response.close();
                return R.error("服务注册失败");
            } else {
                return R.error("服务注册失败");
            }

            // 准备心跳信息
            registerInfo.setOperateCode(202);
            registerInfo.setRegister(null);
            registerInfo.setUrl(NetUtil.getHost() + ":" + port + "/overwatch/connect");

            HeartBeatReg heartBeatReg = new HeartBeatReg(registerInfo, registerIp, registerPort);

            // 注册成功后，开启定时任务，定时向注册发送自身状态
            TimedTask<HeartBeatReg> timedTask = new TimedTask<>(channel, heartBeatReg);
            channel.eventLoop().scheduleAtFixedRate(timedTask, 3, 3, TimeUnit.SECONDS);

            ServerInfo serverInfo = new ServerInfo(UUID.randomUUID().toString(), NetUtil.getHost(), port, new Date(), NetUtil.getHost() + ":" + port);
            // 保存在Map中
            redisTemplate.opsForHash().putIfAbsent(ServerConstants.SERVER_INFO_KEY, NetUtil.getHost() + ":" + port, JSON.toJSONString(serverInfo));
            redisTemplate.expire(ServerConstants.SERVER_INFO_KEY, 30 * 60, TimeUnit.SECONDS);

            // 服务器信息存在redis中 CacheUtil.serverInfoMap.put(NetUtil.getHost() + ":" + port, serverInfo);
            CacheUtil.serverMap.put(NetUtil.getHost() + ":" + port, nettyServer);

            log.info("启动Netty服务，完成：{}", serverInfo);
            return R.ok().put("data", serverInfo);
        } catch (Exception e) {
            log.error("启动Netty服务失败", e);
            return R.error(e.getMessage());
        }
    }

    @DeleteMapping("/{port}")
    public R closeNettyServer(@PathVariable("port") int port) {
        try {
            log.info("关闭Netty服务开始，端口：{}", port);

            nettyServer = CacheUtil.serverMap.get(NetUtil.getHost() + ":" + port);
            if (null == nettyServer) {
                CacheUtil.serverMap.remove(NetUtil.getHost() + ":" + port);
                return R.ok();
            }
            nettyServer.destroy();
            CacheUtil.serverMap.remove(NetUtil.getHost() + ":" + port);
            // {TODO}清除与该服务器关联的用户
            redisTemplate.opsForHash().delete(ServerConstants.SERVER_INFO_KEY, NetUtil.getHost() + ":" + port);

            log.info("关闭Netty服务完成，端口：{}", port);
            return R.ok();
        } catch (Exception e) {
            log.error("关闭Netty服务失败，端口：{}", port, e);
            return R.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public R queryNettyServerList() {
        try {
            final List<Object> values = redisTemplate.opsForHash().values(ServerConstants.SERVER_INFO_KEY);
            final List<ServerInfo> collect = values.stream().map(value -> JSONObject.parseObject(value.toString(), new TypeReference<ServerInfo>(){})).collect(Collectors.toList());

            log.info("查询服务端列表。{}", JSON.toJSONString(collect));
            return R.ok().put("data", collect);
        } catch (Exception e) {
            log.info("查询服务端列表失败。", e);
            return R.error(e.getMessage());
        }
    }
}
