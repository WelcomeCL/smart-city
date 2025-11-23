package com.smartcity.user.interfaces.rest;

import com.smartcity.user.application.dto.*;
import com.smartcity.user.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
// ResponseEntity导入将在代码中使用完全限定名
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// 移除了Valid验证注解的导入

/**
 * 用户REST API控制器
 * 提供用户相关的HTTP接口
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * 创建新用户
     */
    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
    
    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
    
    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    public void resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        userService.resetPassword(email, newPassword);
    }
    
    /**
     * 禁用用户
     */
    @PutMapping("/{id}/disable")
    public void disableUser(@PathVariable Long id) {
        userService.disableUser(id);
    }
    
    /**
     * 启用用户
     */
    @PutMapping("/{id}/enable")
    public void enableUser(@PathVariable Long id) {
        userService.enableUser(id);
    }
}