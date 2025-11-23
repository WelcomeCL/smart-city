package com.smartcity.user.infrastructure.repository;

import com.smartcity.user.domain.entity.User;
import com.smartcity.user.domain.model.UserRepository;
import com.smartcity.user.infrastructure.persistence.UserJpaEntity;
import com.smartcity.user.infrastructure.persistence.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户仓储实现类
 * 实现领域仓储接口，使用JPA进行数据持久化
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    
    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }
    
    @Override
    public User save(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomainEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(entity);
        return savedEntity.toDomainEntity();
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(UserJpaEntity::toDomainEntity);
    }
    
    public void delete(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomainEntity(user);
        userJpaRepository.delete(entity);
    }
    
    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return userJpaRepository.existsById(id);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserJpaEntity::toDomainEntity);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserJpaEntity::toDomainEntity);
    }
    
    @Override
    public Optional<User> findByPhone(String phone) {
        return userJpaRepository.findByPhone(phone)
                .map(UserJpaEntity::toDomainEntity);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByPhone(String phone) {
        return userJpaRepository.existsByPhone(phone);
    }
}