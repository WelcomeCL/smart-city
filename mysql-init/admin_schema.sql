-- 管理模块数据库表结构
-- Schema: admin_schema

-- 如果schema不存在则创建
CREATE SCHEMA IF NOT EXISTS admin_schema DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE admin_schema;

-- 系统配置表
CREATE TABLE IF NOT EXISTS admin_schema.system_configs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '配置ID',
    config_key VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    config_value TEXT NOT NULL COMMENT '配置值',
    config_type VARCHAR(50) COMMENT '配置类型：string, number, boolean, json等',
    description VARCHAR(255) COMMENT '配置描述',
    group_name VARCHAR(50) COMMENT '配置分组',
    is_editable TINYINT DEFAULT 1 COMMENT '是否可编辑：0-不可编辑，1-可编辑',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_config_key (config_key),
    INDEX idx_group_name (group_name)
) ENGINE=InnoDB COMMENT='系统配置表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS admin_schema.operation_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    user_id BIGINT NOT NULL COMMENT '操作用户ID',
    username VARCHAR(50) COMMENT '操作用户名',
    operation_type VARCHAR(50) NOT NULL COMMENT '操作类型：login, logout, create, update, delete, query等',
    module_name VARCHAR(50) COMMENT '模块名称',
    operation_content TEXT COMMENT '操作内容',
    request_url VARCHAR(255) COMMENT '请求URL',
    request_method VARCHAR(20) COMMENT '请求方法',
    client_ip VARCHAR(50) COMMENT '客户端IP',
    user_agent VARCHAR(255) COMMENT '用户代理',
    status TINYINT DEFAULT 1 COMMENT '操作状态：0-失败，1-成功',
    error_message VARCHAR(255) COMMENT '错误信息',
    execution_time INT COMMENT '执行时间（毫秒）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    INDEX idx_user_id (user_id),
    INDEX idx_operation_type (operation_type),
    INDEX idx_module_name (module_name),
    INDEX idx_create_time (create_time),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='操作日志表';

-- 数据字典表
CREATE TABLE IF NOT EXISTS admin_schema.data_dictionary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '字典ID',
    dict_type VARCHAR(50) NOT NULL COMMENT '字典类型',
    dict_code VARCHAR(50) NOT NULL COMMENT '字典编码',
    dict_value VARCHAR(255) NOT NULL COMMENT '字典值',
    dict_text VARCHAR(255) NOT NULL COMMENT '字典文本',
    description VARCHAR(255) COMMENT '描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用：0-禁用，1-启用',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_dict_type_code (dict_type, dict_code),
    INDEX idx_dict_type (dict_type),
    INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB COMMENT='数据字典表';

-- 系统公告表
CREATE TABLE IF NOT EXISTS admin_schema.system_announcements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '公告ID',
    title VARCHAR(100) NOT NULL COMMENT '公告标题',
    content TEXT NOT NULL COMMENT '公告内容',
    announcement_type TINYINT COMMENT '公告类型：1-通知，2-公告，3-新闻',
    publish_status TINYINT DEFAULT 0 COMMENT '发布状态：0-草稿，1-已发布，2-已下线',
    publish_time DATETIME COMMENT '发布时间',
    expire_time DATETIME COMMENT '过期时间',
    view_count INT DEFAULT 0 COMMENT '查看次数',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_publish_status (publish_status),
    INDEX idx_publish_time (publish_time),
    INDEX idx_announcement_type (announcement_type)
) ENGINE=InnoDB COMMENT='系统公告表';

-- 系统监控表
CREATE TABLE IF NOT EXISTS admin_schema.system_monitoring (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '监控ID',
    server_name VARCHAR(100) NOT NULL COMMENT '服务器名称',
    server_ip VARCHAR(50) NOT NULL COMMENT '服务器IP',
    cpu_usage DECIMAL(5,2) COMMENT 'CPU使用率（%）',
    memory_usage DECIMAL(5,2) COMMENT '内存使用率（%）',
    disk_usage DECIMAL(5,2) COMMENT '磁盘使用率（%）',
    network_in DECIMAL(10,2) COMMENT '网络入流量（MB/s）',
    network_out DECIMAL(10,2) COMMENT '网络出流量（MB/s）',
    process_count INT COMMENT '进程数',
    thread_count INT COMMENT '线程数',
    uptime INT COMMENT '运行时间（秒）',
    status TINYINT DEFAULT 1 COMMENT '状态：0-离线，1-在线',
    collect_time DATETIME NOT NULL COMMENT '采集时间',
    INDEX idx_server_name (server_name),
    INDEX idx_server_ip (server_ip),
    INDEX idx_collect_time (collect_time),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='系统监控表';

-- 系统模块表
CREATE TABLE IF NOT EXISTS admin_schema.system_modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '模块ID',
    module_name VARCHAR(100) NOT NULL COMMENT '模块名称',
    module_code VARCHAR(50) NOT NULL UNIQUE COMMENT '模块代码',
    parent_id BIGINT DEFAULT 0 COMMENT '父模块ID',
    url VARCHAR(255) COMMENT '模块URL',
    icon VARCHAR(50) COMMENT '模块图标',
    sort_order INT DEFAULT 0 COMMENT '排序',
    is_menu TINYINT DEFAULT 1 COMMENT '是否为菜单：0-不是，1-是',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用：0-禁用，1-启用',
    description VARCHAR(255) COMMENT '模块描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_module_code (module_code),
    INDEX idx_parent_id (parent_id),
    INDEX idx_is_menu (is_menu)
) ENGINE=InnoDB COMMENT='系统模块表';

-- 定时任务表
CREATE TABLE IF NOT EXISTS admin_schema.scheduled_tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '任务ID',
    task_name VARCHAR(100) NOT NULL COMMENT '任务名称',
    task_code VARCHAR(50) NOT NULL UNIQUE COMMENT '任务代码',
    cron_expression VARCHAR(50) NOT NULL COMMENT 'Cron表达式',
    bean_name VARCHAR(100) NOT NULL COMMENT '任务Bean名称',
    method_name VARCHAR(100) NOT NULL COMMENT '方法名称',
    params TEXT COMMENT '参数',
    status TINYINT DEFAULT 0 COMMENT '状态：0-停止，1-运行',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_task_code (task_code),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='定时任务表';

-- 定时任务日志表
CREATE TABLE IF NOT EXISTS admin_schema.scheduled_task_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    task_name VARCHAR(100) COMMENT '任务名称',
    task_code VARCHAR(50) COMMENT '任务代码',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    execution_time INT COMMENT '执行时间（毫秒）',
    status TINYINT DEFAULT 1 COMMENT '执行状态：0-失败，1-成功',
    error_message TEXT COMMENT '错误信息',
    INDEX idx_task_id (task_id),
    INDEX idx_start_time (start_time),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='定时任务日志表';

-- 插入基础数据
-- 插入系统配置数据
INSERT INTO admin_schema.system_configs (config_key, config_value, config_type, description, group_name) VALUES
('system.name', 'Smart City Management System', 'string', '系统名称', 'basic'),
('system.version', '1.0.0', 'string', '系统版本', 'basic'),
('system.maintenance', 'false', 'boolean', '是否维护模式', 'basic'),
('login.failure.limit', '5', 'number', '登录失败次数限制', 'security'),
('login.lockout.duration', '30', 'number', '登录锁定时长（分钟）', 'security'),
('file.max.size', '10485760', 'number', '文件最大大小（字节）', 'upload'),
('file.allowed.types', 'jpg,jpeg,png,gif,pdf,doc,docx,xls,xlsx', 'string', '允许上传的文件类型', 'upload'),
('cache.enabled', 'true', 'boolean', '是否启用缓存', 'cache'),
('cache.timeout', '3600', 'number', '缓存超时时间（秒）', 'cache'),
('log.level', 'INFO', 'string', '日志级别', 'log')
ON DUPLICATE KEY UPDATE config_value=config_value;

-- 插入数据字典类型
INSERT INTO admin_schema.data_dictionary (dict_type, dict_code, dict_value, dict_text, description, sort_order) VALUES
('user_status', '0', '0', '禁用', '用户状态-禁用', 1),
('user_status', '1', '1', '启用', '用户状态-启用', 2),
('user_gender', '0', '0', '未知', '用户性别-未知', 1),
('user_gender', '1', '1', '男', '用户性别-男', 2),
('user_gender', '2', '2', '女', '用户性别-女', 3),
('operation_type', 'login', 'login', '登录', '操作类型-登录', 1),
('operation_type', 'logout', 'logout', '登出', '操作类型-登出', 2),
('operation_type', 'create', 'create', '创建', '操作类型-创建', 3),
('operation_type', 'update', 'update', '更新', '操作类型-更新', 4),
('operation_type', 'delete', 'delete', '删除', '操作类型-删除', 5),
('operation_type', 'query', 'query', '查询', '操作类型-查询', 6)
ON DUPLICATE KEY UPDATE dict_text=dict_text;

-- 插入系统模块数据
INSERT INTO admin_schema.system_modules (module_name, module_code, parent_id, url, icon, sort_order, is_menu) VALUES
('系统管理', 'system', 0, '#', 'settings', 1, 1),
('用户管理', 'user', 1, '/system/user', 'people', 1, 1),
('角色管理', 'role', 1, '/system/role', 'shield-alt', 2, 1),
('权限管理', 'permission', 1, '/system/permission', 'key', 3, 1),
('系统配置', 'config', 1, '/system/config', 'cog', 4, 1),
('操作日志', 'log', 1, '/system/log', 'file-alt', 5, 1),
('数据字典', 'dict', 1, '/system/dict', 'book', 6, 1),
('定时任务', 'task', 1, '/system/task', 'clock', 7, 1),
('交通管理', 'traffic', 0, '#', 'car', 2, 1),
('道路管理', 'road', 9, '/traffic/road', 'road', 1, 1),
('监测点管理', 'monitor', 9, '/traffic/monitor', 'map-marker-alt', 2, 1),
('交通事件', 'event', 9, '/traffic/event', 'exclamation-triangle', 3, 1),
('信号灯管理', 'light', 9, '/traffic/light', 'traffic-light', 4, 1),
('交通预警', 'alert', 9, '/traffic/alert', 'bell', 5, 1),
('系统监控', 'monitoring', 0, '#', 'chart-line', 3, 1),
('服务器监控', 'server', 15, '/monitoring/server', 'server', 1, 1),
('性能分析', 'performance', 15, '/monitoring/performance', 'chart-bar', 2, 1),
('数据统计', 'dashboard', 0, '/dashboard', 'tachometer-alt', 0, 1)
ON DUPLICATE KEY UPDATE module_name=module_name;

-- 插入定时任务数据
INSERT INTO admin_schema.scheduled_tasks (task_name, task_code, cron_expression, bean_name, method_name, params, status, remark) VALUES
('清理登录日志', 'cleanLoginLog', '0 0 1 * * ?', 'loginLogCleanTask', 'execute', '{"days":30}', 1, '每天凌晨1点清理30天前的登录日志'),
('清理操作日志', 'cleanOperationLog', '0 0 2 * * ?', 'operationLogCleanTask', 'execute', '{"days":90}', 1, '每天凌晨2点清理90天前的操作日志'),
('系统监控数据采集', 'systemMonitor', '0 */5 * * * ?', 'systemMonitorTask', 'execute', '{}', 1, '每5分钟采集一次系统监控数据'),
('数据备份', 'dataBackup', '0 0 3 * * ?', 'dataBackupTask', 'execute', '{"backupDir":"/backup"}', 1, '每天凌晨3点执行数据备份'),
('统计报表生成', 'statisticReport', '0 0 4 * * ?', 'statisticReportTask', 'execute', '{}', 1, '每天凌晨4点生成统计报表')
ON DUPLICATE KEY UPDATE task_name=task_name;