-- Smart City Management System - Database Initialization Script
-- This script executes all module-specific SQL files

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建用户模块数据库和表
SOURCE /Users/chenlei/Project/smart-city/mysql-init/user_schema.sql;

-- 创建交通模块数据库和表
SOURCE /Users/chenlei/Project/smart-city/mysql-init/traffic_schema.sql;

-- 创建管理模块数据库和表
SOURCE /Users/chenlei/Project/smart-city/mysql-init/admin_schema.sql;

-- 创建数据库用户和权限
CREATE USER IF NOT EXISTS 'smartcity'@'%' IDENTIFIED BY 'SmartCity2024!';

-- 授予用户对所有schema的权限
GRANT ALL PRIVILEGES ON user_schema.* TO 'smartcity'@'%';
GRANT ALL PRIVILEGES ON traffic_schema.* TO 'smartcity'@'%';
GRANT ALL PRIVILEGES ON admin_schema.* TO 'smartcity'@'%';

-- 刷新权限
FLUSH PRIVILEGES;

-- 重置外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- 显示创建的数据库信息
SELECT '===== Smart City Database Initialization Complete =====' AS message;
SELECT schema_name AS 'Created Schemas' FROM information_schema.schemata 
WHERE schema_name IN ('user_schema', 'traffic_schema', 'admin_schema');

-- 显示创建的用户信息
SELECT '===== Database Users Created =====' AS message;
SELECT user, host FROM mysql.user WHERE user = 'smartcity';

-- 显示每个schema中的表数量
SELECT '===== Tables Count by Schema =====' AS message;
SELECT table_schema, COUNT(*) AS table_count 
FROM information_schema.tables 
WHERE table_schema IN ('user_schema', 'traffic_schema', 'admin_schema')
GROUP BY table_schema;

-- 显示初始化成功信息
SELECT '===== All SQL Files Executed Successfully =====' AS message;