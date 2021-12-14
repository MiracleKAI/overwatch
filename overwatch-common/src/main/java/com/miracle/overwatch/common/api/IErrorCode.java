package com.miracle.overwatch.common.api;

/**
 * 封装API的错误码
 * @author qiukai
 */
public interface IErrorCode {
    /**
     * 获取状态码
     * @return 状态码
     */
    int getCode();

    /**
     * 状态码的描述
     * @return 描述
     */
    String getMessage();
}
