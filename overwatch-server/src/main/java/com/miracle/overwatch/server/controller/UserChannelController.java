package com.miracle.overwatch.server.controller;

import com.alibaba.fastjson.JSON;
import com.miracle.overwatch.common.api.R;
import com.miracle.overwatch.common.domain.ClientInfo;
import com.miracle.overwatch.server.service.impl.ExtServerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/11/3 8:57 下午
 */
@RestController
@Slf4j
@RequestMapping("/client")
public class UserChannelController {

    @Resource
    private ExtServerService extServerService;

    @GetMapping("/{channelId}")
    public R queryUserChannelInfo(@PathVariable("channelId") String channelId) {
        log.info("查询用户信息开始");
        final ClientInfo clientInfo = extServerService.getRedisUtil().get(channelId);

        log.info("查询用户信息完成: {}", clientInfo);
        if (clientInfo == null) {
            return R.error("无此客户");
        } else {
            return R.ok().put("data", clientInfo);
        }
    }

    @GetMapping("/list")
    public R queryUserChannelInfoList() {
        try {
            log.info("查询用户列表信息开始");

            List<ClientInfo> userChannelInfoList = extServerService.getRedisUtil().popList();

            log.info("查询用户列表信息完成。list：{}", userChannelInfoList);
            return R.ok().put("data", userChannelInfoList);
        } catch (Exception e) {
            log.error("查询用户列表信息失败", e);
            return R.error(e.getMessage());
        }
    }

    @GetMapping("/list/host")
    public R queryUserChannelInfoListByPort(@Param("host") String host, @Param("port") int port) {
        try {
            log.info("查询用户列表信息开始");

            List<ClientInfo> userChannelInfoList = extServerService.getRedisUtil().popList()
                    .stream().filter((userChannelInfo) -> (userChannelInfo.getPort() == port && userChannelInfo.getHost().equals(host)))
                    .collect(Collectors.toList());

            log.info("查询用户列表信息完成。list：{}", JSON.toJSONString(userChannelInfoList));
            return R.ok().put("data", userChannelInfoList);
        } catch (Exception e) {
            log.error("查询用户列表信息失败", e);
            return R.error(e.getMessage());
        }
    }
}
