package com.miracle.overwatch.server.controller;


import com.miracle.overwatch.common.api.R;
import com.miracle.overwatch.common.domain.OperatorOrder;

import com.miracle.overwatch.server.dto.OrderParam;
import com.miracle.overwatch.server.service.impl.OperatorOrderService;
import com.miracle.overwatch.server.util.CacheUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QiuKai
 * @date 2021/10/4 11:09 上午
 */
@RestController
public class OperatorOrderController {

    private final OperatorOrderService operatorOrderService;

    public OperatorOrderController(OperatorOrderService operatorOrderService) {
        this.operatorOrderService = operatorOrderService;
    }

    @PostMapping("/order")
    public R sentOrder(@RequestBody OrderParam orderParam){
        OperatorOrder operatorOrder = new OperatorOrder();
        final String clientChannelId = orderParam.getChannelId();
        final String remoteChannelId = CacheUtil.channelMap.get(clientChannelId);
        operatorOrder.setChannelId(remoteChannelId);
        operatorOrder.setOrder(orderParam.getOrder());
        final boolean b = operatorOrderService.sentOrder(operatorOrder);
        return b ? R.ok("发送成功") : R.error("发送失败");
    }
}
