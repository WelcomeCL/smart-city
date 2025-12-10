package com.smartcity.user.domain.entity;

import java.time.LocalDateTime;

/**
 * 用户聚合根
 * 表示系统中的用户实体，是用户领域的核心概念
 */
public class User {
    
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * 构造函数，创建新用户
     */
    public User(Long id, String username, String password, String email, String phone) {
        // 不再调用super(id)，直接设置id字段
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * 构造函数，创建带角色的新用户
     */
    public User(Long id, String username, String password, String email, String phone, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // 添加getter方法
    public Long getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public UserStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // 设置密码
    public void setPassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 设置角色
    public void setRole(UserRole role) {
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 更新用户信息
    public void update(String email, String phone) {
        this.email = email;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 更新用户信息（包含角色）
    public void update(String email, String phone, UserRole role) {
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 禁用用户
    public void disable() {
        this.status = UserStatus.DISABLED;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 启用用户
    public void enable() {
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 重置密码
    public void resetPassword(String newPassword) {
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * 用户角色枚举
     */
    public enum UserRole {
        USER,      // 普通用户
        ADMIN      // 管理员
    }
    
    /**
     * 用户状态枚举
     */
    public enum UserStatus {
        ACTIVE,    // 激活状态
        DISABLED,  // 禁用状态
        LOCKED     // 锁定状态
    }
}