package com.miracle.overwatch.common.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author qiukai
 */
public enum ResultCode implements IErrorCode {
    /**
     * 状态码
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "未知异常，请联系管理员"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
