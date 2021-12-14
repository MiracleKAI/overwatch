package com.miracle.overwatch.common.domain.protocol;

/**
 * @author QiuKai
 * @date 2021/10/2 8:35 下午
 */
public class MsgType {

    private MsgType(){}

    /**
     * 操作指令，用于操作client
     */
    public static final Byte OPERATOR_ORDER = 1;
    public static final Byte STATUS_INFO = 2;
    public static final Byte HEART_BEAT = 3;
    public static final Byte CLIENT_INFO = 4;
}
