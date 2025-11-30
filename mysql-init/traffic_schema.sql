-- 交通模块数据库表结构
-- Schema: traffic_db

-- 如果schema不存在则创建
CREATE SCHEMA IF NOT EXISTS traffic_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE traffic_db;

-- 道路信息表
CREATE TABLE IF NOT EXISTS traffic_db.roads (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '道路ID',
    road_name VARCHAR(100) NOT NULL COMMENT '道路名称',
    road_code VARCHAR(50) NOT NULL UNIQUE COMMENT '道路编号',
    road_type TINYINT NOT NULL COMMENT '道路类型：1-高速公路，2-国道，3-省道，4-城市主干道，5-城市次干道，6-支路',
    start_point VARCHAR(100) COMMENT '起点位置',
    end_point VARCHAR(100) COMMENT '终点位置',
    length DECIMAL(10,2) COMMENT '道路长度（公里）',
    width DECIMAL(5,2) COMMENT '道路宽度（米）',
    lanes INT COMMENT '车道数',
    speed_limit INT COMMENT '限速（公里/小时）',
    status TINYINT DEFAULT 1 COMMENT '状态：0-关闭，1-正常通行',
    description TEXT COMMENT '道路描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_road_name (road_name),
    INDEX idx_road_code (road_code),
    INDEX idx_road_type (road_type)
) ENGINE=InnoDB COMMENT='道路信息表';

-- 交通流量监测点表
CREATE TABLE IF NOT EXISTS traffic_db.monitoring_points (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '监测点ID',
    point_name VARCHAR(100) NOT NULL COMMENT '监测点名称',
    point_code VARCHAR(50) NOT NULL UNIQUE COMMENT '监测点编号',
    road_id BIGINT NOT NULL COMMENT '所属道路ID',
    latitude DECIMAL(10,6) NOT NULL COMMENT '纬度',
    longitude DECIMAL(10,6) NOT NULL COMMENT '经度',
    direction TINYINT COMMENT '监测方向：1-上行，2-下行，3-双向',
    installation_date DATE COMMENT '安装日期',
    status TINYINT DEFAULT 1 COMMENT '状态：0-故障，1-正常',
    last_maintain_date DATE COMMENT '最后维护日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (road_id) REFERENCES traffic_db.roads(id) ON DELETE CASCADE,
    INDEX idx_road_id (road_id),
    INDEX idx_point_code (point_code)
) ENGINE=InnoDB COMMENT='交通流量监测点表';

-- 交通流量数据表
CREATE TABLE IF NOT EXISTS traffic_db.traffic_flows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '流量数据ID',
    monitoring_point_id BIGINT NOT NULL COMMENT '监测点ID',
    flow_value INT NOT NULL COMMENT '流量值（辆/小时）',
    average_speed DECIMAL(6,2) COMMENT '平均车速（公里/小时）',
    occupancy_rate DECIMAL(5,2) COMMENT '占有率（%）',
    congestion_level TINYINT COMMENT '拥堵等级：1-畅通，2-基本畅通，3-轻度拥堵，4-中度拥堵，5-严重拥堵',
    data_time DATETIME NOT NULL COMMENT '数据采集时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    FOREIGN KEY (monitoring_point_id) REFERENCES traffic_db.monitoring_points(id) ON DELETE CASCADE,
    INDEX idx_monitoring_point_id (monitoring_point_id),
    INDEX idx_data_time (data_time),
    INDEX idx_congestion_level (congestion_level)
) ENGINE=InnoDB COMMENT='交通流量数据表';

-- 交通事件表
CREATE TABLE IF NOT EXISTS traffic_db.traffic_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '事件ID',
    event_type TINYINT NOT NULL COMMENT '事件类型：1-交通事故，2-道路施工，3-车辆抛锚，4-恶劣天气，5-交通管制，6-其他',
    road_id BIGINT NOT NULL COMMENT '所属道路ID',
    location_description VARCHAR(255) NOT NULL COMMENT '位置描述',
    latitude DECIMAL(10,6) NOT NULL COMMENT '纬度',
    longitude DECIMAL(10,6) NOT NULL COMMENT '经度',
    severity TINYINT COMMENT '严重程度：1-轻微，2-一般，3-严重，4-特别严重',
    impact_level TINYINT COMMENT '影响程度：1-无影响，2-轻微影响，3-中度影响，4-严重影响',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    status TINYINT DEFAULT 1 COMMENT '状态：0-已处理，1-处理中，2-待处理',
    description TEXT COMMENT '事件描述',
    created_by BIGINT COMMENT '创建人ID',
    updated_by BIGINT COMMENT '更新人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (road_id) REFERENCES traffic_db.roads(id) ON DELETE CASCADE,
    INDEX idx_event_type (event_type),
    INDEX idx_road_id (road_id),
    INDEX idx_start_time (start_time),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='交通事件表';

-- 信号灯控制表
CREATE TABLE IF NOT EXISTS traffic_db.traffic_lights (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '信号灯ID',
    light_name VARCHAR(100) NOT NULL COMMENT '信号灯名称',
    light_code VARCHAR(50) NOT NULL UNIQUE COMMENT '信号灯编号',
    intersection_name VARCHAR(100) COMMENT '路口名称',
    latitude DECIMAL(10,6) NOT NULL COMMENT '纬度',
    longitude DECIMAL(10,6) NOT NULL COMMENT '经度',
    status TINYINT DEFAULT 1 COMMENT '状态：0-故障，1-正常，2-手动控制',
    cycle_time INT COMMENT '周期时间（秒）',
    red_time INT COMMENT '红灯时间（秒）',
    green_time INT COMMENT '绿灯时间（秒）',
    yellow_time INT COMMENT '黄灯时间（秒）',
    current_phase TINYINT COMMENT '当前相位：1-红灯，2-绿灯，3-黄灯',
    last_change_time DATETIME COMMENT '最后状态变化时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_light_code (light_code),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='信号灯控制表';

-- 交通预警表
CREATE TABLE IF NOT EXISTS traffic_db.traffic_alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '预警ID',
    alert_type TINYINT NOT NULL COMMENT '预警类型：1-拥堵预警，2-事故预警，3-恶劣天气预警，4-道路施工预警',
    title VARCHAR(100) NOT NULL COMMENT '预警标题',
    content TEXT NOT NULL COMMENT '预警内容',
    affected_area VARCHAR(255) COMMENT '影响区域',
    severity TINYINT COMMENT '严重程度：1-低，2-中，3-高',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    status TINYINT DEFAULT 1 COMMENT '状态：0-已解除，1-生效中',
    created_by BIGINT COMMENT '创建人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_alert_type (alert_type),
    INDEX idx_start_time (start_time),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='交通预警表';

-- 交通摄像头表
CREATE TABLE IF NOT EXISTS traffic_db.traffic_cameras (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '摄像头ID',
    camera_name VARCHAR(100) NOT NULL COMMENT '摄像头名称',
    camera_code VARCHAR(50) NOT NULL UNIQUE COMMENT '摄像头编号',
    road_id BIGINT NOT NULL COMMENT '所属道路ID',
    latitude DECIMAL(10,6) NOT NULL COMMENT '纬度',
    longitude DECIMAL(10,6) NOT NULL COMMENT '经度',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    rtsp_url VARCHAR(255) COMMENT 'RTSP流地址',
    status TINYINT DEFAULT 1 COMMENT '状态：0-离线，1-在线',
    installation_date DATE COMMENT '安装日期',
    last_maintain_date DATE COMMENT '最后维护日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (road_id) REFERENCES traffic_db.roads(id) ON DELETE CASCADE,
    INDEX idx_road_id (road_id),
    INDEX idx_camera_code (camera_code),
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='交通摄像头表';

-- 插入基础数据
-- 插入道路数据
INSERT INTO traffic_db.roads (road_name, road_code, road_type, length, lanes, speed_limit) VALUES
('中山路', 'RD001', 4, 15.5, 6, 60),
('解放路', 'RD002', 4, 12.3, 4, 60),
('环城高速', 'RD003', 1, 45.8, 8, 120),
('东环路', 'RD004', 5, 8.2, 4, 50),
('西湖大道', 'RD005', 4, 10.5, 6, 60)
ON DUPLICATE KEY UPDATE road_name=road_name;

-- 插入监测点数据
INSERT INTO traffic_db.monitoring_points (point_name, point_code, road_id, latitude, longitude, direction) VALUES
('中山路监测点1', 'MP001', 1, 31.230416, 121.473701, 3),
('解放路监测点1', 'MP002', 2, 31.235618, 121.469648, 3),
('环城高速监测点1', 'MP003', 3, 31.243579, 121.483671, 1),
('环城高速监测点2', 'MP004', 3, 31.243679, 121.484671, 2),
('东环路监测点1', 'MP005', 4, 31.228416, 121.478701, 3),
('西湖大道监测点1', 'MP006', 5, 31.225416, 121.463701, 3)
ON DUPLICATE KEY UPDATE point_name=point_name;

-- 插入信号灯数据
INSERT INTO traffic_db.traffic_lights (light_name, light_code, intersection_name, latitude, longitude, cycle_time, red_time, green_time, yellow_time) VALUES
('中山路-解放路路口', 'TL001', '中山解路口', 31.232416, 121.471701, 120, 60, 55, 5),
('中山路-东环路路口', 'TL002', '中山东环路口', 31.230416, 121.476701, 90, 45, 40, 5),
('西湖大道-解放路路口', 'TL003', '西湖解路口', 31.225416, 121.466701, 100, 50, 45, 5)
ON DUPLICATE KEY UPDATE light_name=light_name;