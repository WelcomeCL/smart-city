package com.smartcity.user.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 统一处理系统中的所有异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理表单验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        BindingResult result = ex.getBindingResult();
        
        // 收集所有验证错误
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("message", "请求参数验证失败");
        response.put("errors", errors);
        response.put("timestamp", System.currentTimeMillis());
        
        return response;
    }
    
    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGeneralExceptions(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage() != null ? ex.getMessage() : "系统内部错误");
        response.put("timestamp", System.currentTimeMillis());
        
        return response;
    }
}