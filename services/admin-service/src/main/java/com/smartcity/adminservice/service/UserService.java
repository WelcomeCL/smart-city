package com.smartcity.adminservice.service;

import com.smartcity.adminservice.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * 用户管理服务接口
 */
public interface UserService {

    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户对象
     */
    Optional<User> getUserById(Long id);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);

    /**
     * 创建新用户
     * @param user 用户对象
     * @return 创建的用户对象
     */
    User createUser(User user);

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param user 用户对象
     * @return 更新后的用户对象
     */
    User updateUser(Long id, User user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
}