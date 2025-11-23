package com.smartcity.user.application.dto;

/**
 * 更新用户请求DTO
 * 用于接收更新用户的请求参数
 */
public class UpdateUserRequest {
    
    private String email;
    
    private String phone;
    
    // Getter methods
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    
    // Setter methods
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}