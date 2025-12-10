package com.smartcity.user.application.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * 更新用户请求DTO
 * 用于接收更新用户的请求参数
 */
public class UpdateUserRequest {
    
    @Email(message = "请输入正确的邮箱格式")
    private String email;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号格式")
    private String phone;
    
    private String role;
    
    // Getter methods
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    
    // Setter methods
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRole(String role) { this.role = role; }
}