package com.smartcity.adminservice.service.impl;

import com.smartcity.adminservice.entity.User;
import com.smartcity.adminservice.mapper.UserMapper;
import com.smartcity.adminservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户管理服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
        // 密码加密处理
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userMapper.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setRealName(user.getRealName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPhone(user.getPhone());
            updatedUser.setStatus(user.getStatus());
            updatedUser.setRole(user.getRole());
            // 如果密码不为空，则更新密码
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                updatedUser.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            }
            updatedUser.setUpdateTime(new Date());
            return userMapper.save(updatedUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userMapper.existsByUsername(username);
    }
}