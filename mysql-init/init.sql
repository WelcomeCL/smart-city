-- 创建用户数据库
CREATE DATABASE IF NOT EXISTS user_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建交通数据库
CREATE DATABASE IF NOT EXISTS traffic_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 授予权限
GRANT ALL PRIVILEGES ON user_db.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON traffic_db.* TO 'admin'@'%';

-- 刷新权限
FLUSH PRIVILEGES;

-- 切换到用户数据库
USE user_db;

-- 创建用户表（如果需要）
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 切换到交通数据库
USE traffic_db;

-- 创建交通数据表（如果需要）
CREATE TABLE IF NOT EXISTS traffic_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    congestion_level VARCHAR(20),
    vehicle_count INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);