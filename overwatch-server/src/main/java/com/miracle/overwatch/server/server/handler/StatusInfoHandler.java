package com.miracle.overwatch.server.server.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miracle.overwatch.common.domain.StatusInfo;
import com.miracle.overwatch.server.mbg.mapper.StatusInfoDao;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * @author QiuKai
 * @date 2021/10/5 7:19 下午
 */
@Slf4j
@Component
public class StatusInfoHandler extends SimpleChannelInboundHandler<StatusInfo> {

    @Autowired
    private StatusInfoDao statusInfoDao;

    public static StatusInfoHandler statusInfoHandler;

    @PostConstruct
    public void init() {
        statusInfoHandler = this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StatusInfo msg) throws Exception {
        log.info("status_info:{}", JSON.toJSON(msg));
        assert statusInfoHandler.statusInfoDao != null;
        statusInfoHandler.statusInfoDao.insert(msg);

        //TODO 接受客户端的状态，对该状态进行保存
    }
}
