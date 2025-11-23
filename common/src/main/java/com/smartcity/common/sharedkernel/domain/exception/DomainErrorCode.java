package com.smartcity.common.sharedkernel.domain.exception;

import lombok.Getter;

/**
 * 领域错误码枚举类
 * 定义领域中常见的错误码
 */
@Getter
public enum DomainErrorCode {
    // 通用错误码
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统异常"),
    BUSINESS_ERROR(400, "业务异常"),
    RESOURCE_NOT_FOUND(404, "资源不存在"),
    PARAM_VALIDATE_ERROR(400, "参数校验失败"),
    
    // DDD特定错误码
    AGGREGATE_ROOT_NOT_FOUND(404, "聚合根不存在"),
    DOMAIN_RULE_VIOLATION(400, "违反领域规则"),
    ENTITY_STATE_ERROR(400, "实体状态错误"),
    VALUE_OBJECT_VALIDATION_ERROR(400, "值对象验证失败");

    private final int code;
    private final String message;

    DomainErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}