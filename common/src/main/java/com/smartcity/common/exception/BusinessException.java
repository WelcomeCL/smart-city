package com.smartcity.common.exception;

/**
 * 业务异常类
 * 用于处理业务逻辑中的异常情况
 */
public class BusinessException extends RuntimeException {
    
    private final ErrorCode errorCode;
    private final String detailMessage;
    
    /**
     * 使用错误码构造业务异常
     * @param errorCode 错误码枚举
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }
    
    /**
     * 使用错误码和自定义消息构造业务异常
     * @param errorCode 错误码枚举
     * @param message 自定义消息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.detailMessage = message;
    }
    
    /**
     * 使用错误码、自定义消息和原因构造业务异常
     * @param errorCode 错误码枚举
     * @param message 自定义消息
     * @param cause 异常原因
     */
    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.detailMessage = message;
    }
    
    /**
     * 使用错误码和原因构造业务异常
     * @param errorCode 错误码枚举
     * @param cause 异常原因
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }
    
    public String getDetailMessage() {
        return detailMessage;
    }
    
    public int getStatusCode() {
        return errorCode.getCode();
    }
}