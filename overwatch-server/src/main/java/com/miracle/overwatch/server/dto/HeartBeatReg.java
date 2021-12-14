package com.miracle.overwatch.server.dto;

import com.alibaba.fastjson.JSON;

import com.miracle.overwatch.common.api.ResultCode;
import com.miracle.overwatch.common.domain.protocol.Task;
import com.miracle.overwatch.common.util.HttpUtils;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/11/8 12:40 上午
 */
@Slf4j
@AllArgsConstructor
public class HeartBeatReg implements Task {
    private RegisterInfo registerInfo;
    private String registerIp;
    private String registerPort;


    @Override
    public void executeTask(Channel channel) {
        // 心跳
        log.info("registerInfo:{}", registerInfo);
        final Response response = HttpUtils.doPost("http://" + this.registerIp + ":" + this.registerPort + "/push", JSON.toJSONString(this.registerInfo));

        if (response != null && response.isSuccessful()) {
            if (ResultCode.SUCCESS.getCode() != response.code()) {
                log.error("心跳失败");
            }
            response.close();
        }else if (response != null){
            response.close();
            log.error("心跳失败");
        }else {
            log.error("心跳失败");
        }
    }
}
