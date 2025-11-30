package com.smartcity.common.exception;

/**
 * 错误码枚举类
 */
public enum ErrorCode {
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统异常"),
    BUSINESS_ERROR(400, "业务异常"),
    RESOURCE_NOT_FOUND(404, "资源不存在"),
    PARAM_VALIDATE_ERROR(400, "参数校验失败");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取错误消息
     * @return 错误消息
     */
    public String getMessage() {
        return message;
    }
}