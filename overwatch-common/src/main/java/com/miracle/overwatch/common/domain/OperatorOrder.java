package com.miracle.overwatch.common.domain;


import com.miracle.overwatch.common.domain.protocol.MsgType;
import com.miracle.overwatch.common.domain.protocol.Packet;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author QiuKai
 * @date 2021/10/2 8:36 下午
 */
@Setter
@Getter
public class OperatorOrder extends Packet {

    private Byte orderType;

    private Map<String, String> order = new HashMap<>();

    @Override
    public Byte getType() {
        return MsgType.OPERATOR_ORDER;
    }
}
