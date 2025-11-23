package com.smartcity.user.application.dto;

/**
 * 创建用户请求DTO
 * 用于接收创建用户的请求参数
 */
public class CreateUserRequest {
    
    private String username;
    
    private String password;
    
    private String email;
    
    private String phone;
    
    // Getter methods
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    
    // Setter methods
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}