package com.smartcity.user.application.dto;

/**
 * 登录响应DTO
 * 用于向客户端返回登录结果
 */
public class LoginResponse {
    
    private UserResponse user;
    private String token;
    
    // 无参构造器
    public LoginResponse() {}
    
    // 有参构造器
    public LoginResponse(UserResponse user, String token) {
        this.user = user;
        this.token = token;
    }
    
    // Getter methods
    public UserResponse getUser() { return user; }
    public String getToken() { return token; }
    
    // Setter methods
    public void setUser(UserResponse user) { this.user = user; }
    public void setToken(String token) { this.token = token; }
}