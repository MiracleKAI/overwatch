package com.miracle.overwatch.common.util;

import com.alibaba.fastjson.JSON;
import com.miracle.overwatch.common.domain.protocol.Packet;

/**
 * @author QiuKai
 * @date 2021/10/4 3:09 下午
 */
public class MsgUtil {
    private MsgUtil(){}

    public static Packet json2Obj(String json){
        return JSON.parseObject(json, Packet.class);
    }
}
