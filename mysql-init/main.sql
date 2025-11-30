-- Smart City Management System - Database Initialization Script
-- This script executes all module-specific SQL files

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建用户模块数据库和表
SOURCE /docker-entrypoint-initdb.d/user_schema.sql;

-- 创建交通模块数据库和表
SOURCE /docker-entrypoint-initdb.d/traffic_schema.sql;

-- 创建管理模块数据库和表
SOURCE /docker-entrypoint-initdb.d/admin_schema.sql;

-- 刷新权限
FLUSH PRIVILEGES;

-- 重置外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- 显示创建的数据库信息
SELECT '===== Smart City Database Initialization Complete =====' AS message;
SELECT schema_name AS 'Created Schemas' FROM information_schema.schemata 
WHERE schema_name IN ('user_db', 'traffic_db', 'admin_db');

-- 显示每个schema中的表数量
SELECT '===== Tables Count by Schema =====' AS message;
SELECT table_schema, COUNT(*) AS table_count 
FROM information_schema.tables 
WHERE table_schema IN ('user_db', 'traffic_db', 'admin_db')
GROUP BY table_schema;

-- 显示初始化成功信息
SELECT '===== All SQL Files Executed Successfully =====' AS message;