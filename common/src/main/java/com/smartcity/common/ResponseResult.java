package com.smartcity.common;

import com.smartcity.common.exception.ErrorCode;

/**
 * 通用响应结果封装类
 * 统一API响应格式，格式为：{ code: 200, data: {}, message: '' }
 * @param <T> 响应数据类型
 */
public class ResponseResult<T> {
    
    /**
     * 响应状态码
     */
    private int code;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 私有构造方法，防止直接实例化
     * @param code 状态码
     * @param data 数据
     * @param message 消息
     */
    private ResponseResult(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    
    /**
     * 成功响应，不带数据
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ErrorCode.SUCCESS.getCode(), null, ErrorCode.SUCCESS.getMessage());
    }
    
    /**
     * 成功响应，带数据
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMessage());
    }
    
    /**
     * 成功响应，带数据和自定义消息
     * @param data 响应数据
     * @param message 自定义消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<>(ErrorCode.SUCCESS.getCode(), data, message);
    }
    
    /**
     * 失败响应，使用错误码
     * @param errorCode 错误码枚举
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> fail(ErrorCode errorCode) {
        return new ResponseResult<>(errorCode.getCode(), null, errorCode.getMessage());
    }
    
    /**
     * 失败响应，使用错误码和自定义消息
     * @param errorCode 错误码枚举
     * @param message 自定义消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> fail(ErrorCode errorCode, String message) {
        return new ResponseResult<>(errorCode.getCode(), null, message);
    }
    
    /**
     * 失败响应，使用自定义状态码和消息
     * @param code 状态码
     * @param message 消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseResult<T> fail(int code, String message) {
        return new ResponseResult<>(code, null, message);
    }
    
    // getter方法
    public int getCode() {
        return code;
    }
    
    public T getData() {
        return data;
    }
    
    public String getMessage() {
        return message;
    }
    
    // setter方法（可选，根据需要使用）
    public void setCode(int code) {
        this.code = code;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}