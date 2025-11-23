package com.smartcity.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA的用户仓储接口
 * 提供底层数据访问能力
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    
    /**
     * 根据用户名查找用户
     */
    Optional<UserJpaEntity> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<UserJpaEntity> findByEmail(String email);
    
    /**
     * 根据手机号查找用户
     */
    Optional<UserJpaEntity> findByPhone(String phone);
    
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
}