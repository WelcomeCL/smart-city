package com.smartcity.user.application.service.impl;


import com.smartcity.user.application.dto.*;
import com.smartcity.user.application.service.UserService;
import com.smartcity.user.domain.entity.User;
import com.smartcity.user.domain.model.UserRepository;
import com.smartcity.user.domain.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户应用服务实现类
 * 实现应用服务接口，调用领域层完成业务功能
 */
@Service
public class UserServiceImpl implements UserService {
    
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserDomainService userDomainService, UserRepository userRepository) {
        this.userDomainService = userDomainService;
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        // 使用领域服务创建用户
        User user = userDomainService.createUser(
                null,  // ID由数据库自动生成
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getPhone()
        );
        
        // 转换为响应DTO
        return convertToResponse(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        
        return convertToResponse(user);
    }
    
    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        
        // 更新用户信息
        user.update(request.getEmail(), request.getPhone());
        User updatedUser = userRepository.save(user);
        
        return convertToResponse(updatedUser);
    }
    
    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // 使用领域服务进行认证
        User user = userDomainService.authenticate(request.getUsername(), request.getPassword());
        
        // 生成登录响应（实际应用中应包含token等信息）
        return new LoginResponse(convertToResponse(user), "sample-jwt-token");
    }
    
    @Override
    @Transactional
    public void resetPassword(String email, String newPassword) {
        userDomainService.resetPassword(email, newPassword);
    }
    
    @Override
    @Transactional
    public void disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        
        user.disable();
        userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void enableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        
        user.enable();
        userRepository.save(user);
    }
    
    /**
     * 将领域实体转换为响应DTO
     */
    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setStatus(user.getStatus().name());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}