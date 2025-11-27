-- 用户模块数据库表结构
-- Schema: user_schema

-- 如果schema不存在则创建
CREATE SCHEMA IF NOT EXISTS user_schema DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE user_schema;

-- 用户表
CREATE TABLE IF NOT EXISTS user_schema.users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    full_name VARCHAR(100) COMMENT '真实姓名',
    avatar VARCHAR(255) COMMENT '头像URL',
    gender TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    birth_date DATE COMMENT '出生日期',
    address VARCHAR(255) COMMENT '地址',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    last_login_time DATETIME COMMENT '最后登录时间',
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_phone (phone)
) ENGINE=InnoDB COMMENT='用户表';

-- 用户角色表
CREATE TABLE IF NOT EXISTS user_schema.roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
    description VARCHAR(255) COMMENT '角色描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_role_name (role_name)
) ENGINE=InnoDB COMMENT='角色表';

-- 用户-角色关联表
CREATE TABLE IF NOT EXISTS user_schema.user_roles (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user_schema.users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES user_schema.roles(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_role_id (role_id)
) ENGINE=InnoDB COMMENT='用户-角色关联表';

-- 权限表
CREATE TABLE IF NOT EXISTS user_schema.permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '权限ID',
    permission_code VARCHAR(100) NOT NULL UNIQUE COMMENT '权限代码',
    permission_name VARCHAR(100) NOT NULL COMMENT '权限名称',
    description VARCHAR(255) COMMENT '权限描述',
    resource_type VARCHAR(50) COMMENT '资源类型',
    resource_path VARCHAR(255) COMMENT '资源路径',
    action VARCHAR(50) COMMENT '操作类型',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_permission_code (permission_code)
) ENGINE=InnoDB COMMENT='权限表';

-- 角色-权限关联表
CREATE TABLE IF NOT EXISTS user_schema.role_permissions (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES user_schema.roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES user_schema.permissions(id) ON DELETE CASCADE,
    INDEX idx_role_id (role_id),
    INDEX idx_permission_id (permission_id)
) ENGINE=InnoDB COMMENT='角色-权限关联表';

-- 用户登录日志表
CREATE TABLE IF NOT EXISTS user_schema.login_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    login_ip VARCHAR(50) COMMENT '登录IP地址',
    login_device VARCHAR(255) COMMENT '登录设备',
    login_browser VARCHAR(255) COMMENT '登录浏览器',
    login_status TINYINT DEFAULT 1 COMMENT '登录状态：0-失败，1-成功',
    error_message VARCHAR(255) COMMENT '错误信息',
    FOREIGN KEY (user_id) REFERENCES user_schema.users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_login_time (login_time)
) ENGINE=InnoDB COMMENT='用户登录日志表';

-- 插入基础数据
-- 插入默认角色
INSERT INTO user_schema.roles (role_name, description) VALUES
('ADMIN', '系统管理员'),
('USER', '普通用户'),
('GUEST', '访客')
ON DUPLICATE KEY UPDATE role_name=role_name;

-- 插入默认权限
INSERT INTO user_schema.permissions (permission_code, permission_name, description, resource_type, resource_path, action) VALUES
('USER_VIEW', '查看用户', '查看用户信息', 'USER', '/users', 'GET'),
('USER_CREATE', '创建用户', '创建新用户', 'USER', '/users', 'POST'),
('USER_UPDATE', '更新用户', '更新用户信息', 'USER', '/users', 'PUT'),
('USER_DELETE', '删除用户', '删除用户', 'USER', '/users', 'DELETE'),
('ROLE_VIEW', '查看角色', '查看角色信息', 'ROLE', '/roles', 'GET'),
('ROLE_CREATE', '创建角色', '创建新角色', 'ROLE', '/roles', 'POST'),
('ROLE_UPDATE', '更新角色', '更新角色信息', 'ROLE', '/roles', 'PUT'),
('ROLE_DELETE', '删除角色', '删除角色', 'ROLE', '/roles', 'DELETE')
ON DUPLICATE KEY UPDATE permission_code=permission_code;

-- 为管理员角色分配权限
INSERT INTO user_schema.role_permissions (role_id, permission_id) 
SELECT r.id, p.id 
FROM user_schema.roles r, user_schema.permissions p 
WHERE r.role_name = 'ADMIN'
ON DUPLICATE KEY UPDATE role_id=r.id;

-- 为普通用户分配部分权限
INSERT INTO user_schema.role_permissions (role_id, permission_id) 
SELECT r.id, p.id 
FROM user_schema.roles r, user_schema.permissions p 
WHERE r.role_name = 'USER' AND p.permission_code IN ('USER_VIEW')
ON DUPLICATE KEY UPDATE role_id=r.id;