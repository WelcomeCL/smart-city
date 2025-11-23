package com.smartcity.common.sharedkernel.domain.exception;

/**
 * 领域异常基类
 * 表示领域中发生的业务规则违反或业务错误
 */
public class DomainException extends RuntimeException {
    
    private final String errorCode;
    
    public DomainException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public DomainException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}