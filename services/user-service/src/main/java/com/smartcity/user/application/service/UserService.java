package com.smartcity.user.application.service;

import com.smartcity.user.application.dto.CreateUserRequest;
import com.smartcity.user.application.dto.UpdateUserRequest;
import com.smartcity.user.application.dto.UserResponse;
import com.smartcity.user.application.dto.LoginRequest;
import com.smartcity.user.application.dto.LoginResponse;

/**
 * 用户应用服务接口
 * 定义应用层提供的用户相关功能，作为领域层和表现层之间的桥梁
 */
public interface UserService {
    
    /**
     * 创建新用户
     */
    UserResponse createUser(CreateUserRequest request);
    
    /**
     * 根据ID获取用户信息
     */
    UserResponse getUserById(Long id);
    
    /**
     * 更新用户信息
     */
    UserResponse updateUser(Long id, UpdateUserRequest request);
    
    /**
     * 删除用户
     */
    void deleteUser(Long id);
    
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 重置密码
     */
    void resetPassword(String email, String newPassword);
    
    /**
     * 禁用用户
     */
    void disableUser(Long id);
    
    /**
     * 启用用户
     */
    void enableUser(Long id);
}