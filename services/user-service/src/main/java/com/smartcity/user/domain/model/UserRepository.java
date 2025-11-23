package com.smartcity.user.domain.model;

import com.smartcity.user.domain.entity.User;
import java.util.Optional;

/**
 * 用户仓储接口
 * 定义对用户实体的持久化操作
 */
public interface UserRepository {
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据手机号查找用户
     */
    Optional<User> findByPhone(String phone);
    
    /**
     * 检查用户名是否已存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否已存在
     */
    boolean existsByEmail(String email);
    
    /**
     * 检查手机号是否已存在
     */
    boolean existsByPhone(String phone);
    
    /**
     * 根据ID查找用户
     */
    Optional<User> findById(Long id);
    
    /**
     * 保存用户
     */
    User save(User user);
    
    /**
     * 根据ID删除用户
     */
    void deleteById(Long id);
}