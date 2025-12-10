package com.smartcity.user.domain.service;

import com.smartcity.user.domain.entity.User;
import com.smartcity.user.domain.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户领域服务
 * 封装涉及用户的核心业务逻辑，特别是需要多实体协作的复杂操作
 */
@Service
public class UserDomainService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * 创建新用户，包含验证逻辑
     */
    public User createUser(Long id, String username, String password, String email, String phone) {
        return createUser(id, username, password, email, phone, User.UserRole.USER);
    }
    
    /**
     * 创建新用户，包含验证逻辑和角色支持
     */
    public User createUser(Long id, String username, String password, String email, String phone, User.UserRole role) {
        // 验证唯一性约束
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("用户名已存在");
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("邮箱已存在");
        }
        
        if (userRepository.existsByPhone(phone)) {
            throw new RuntimeException("手机号已存在");
        }
        
        // 创建用户实体
        User user = new User(id, username, password, email, phone, role);
        
        // 保存用户
        return userRepository.save(user);
    }
    
    /**
     * 用户登录验证
     */
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + username));
        
        // 检查用户状态
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new RuntimeException("用户账户状态异常");
        }
        
        // 验证密码（实际应用中应该使用密码加密比较）
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        
        return user;
    }
    
    /**
     * 重置用户密码
     */
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + email));
        
        user.resetPassword(newPassword);
        userRepository.save(user);
    }
}