package com.smartcity.traffic.domain;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 交通信息领域实体类
 */
@NoArgsConstructor
@AllArgsConstructor
public class Traffic {

    private Long id;
    private String location;
    private TrafficStatus status;
    private Integer congestionLevel; // 1-10，1表示畅通，10表示严重拥堵
    private String description;

    /**
     * 交通状态枚举
     */
    public enum TrafficStatus {
        NORMAL, // 正常
        CONGESTED, // 拥堵
        BLOCKED, // 阻塞
        ACCIDENT // 事故
    }

    /**
     * 有参构造函数（不含id）
     */
    public Traffic(String location, TrafficStatus status, Integer congestionLevel, String description) {
        this.location = location;
        this.status = status;
        this.congestionLevel = congestionLevel;
        this.description = description;
    }

    /**
     * 更新拥堵级别
     */
    public void updateCongestionLevel(Integer congestionLevel) {
        if (congestionLevel == null || congestionLevel < 1 || congestionLevel > 10) {
            throw new IllegalArgumentException("拥堵级别必须在1-10之间");
        }
        this.congestionLevel = congestionLevel;
        // 根据拥堵级别自动更新状态
        updateStatusByCongestionLevel();
    }

    /**
     * 更新状态
     */
    public void updateStatus(TrafficStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("交通状态不能为空");
        }
        this.status = status;
    }

    /**
     * 更新位置
     */
    public void updateLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("位置信息不能为空");
        }
        this.location = location;
    }

    /**
     * 更新描述
     */
    public void updateDescription(String description) {
        this.description = description;
    }

    /**
     * 根据拥堵级别自动更新状态
     */
    private void updateStatusByCongestionLevel() {
        if (congestionLevel <= 3) {
            this.status = TrafficStatus.NORMAL;
        } else if (congestionLevel <= 7) {
            this.status = TrafficStatus.CONGESTED;
        } else {
            this.status = TrafficStatus.BLOCKED;
        }
    }

    /**
     * 获取标识符
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置标识符
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for status
     */
    public TrafficStatus getStatus() {
        return status;
    }

    /**
     * Getter for congestionLevel
     */
    public Integer getCongestionLevel() {
        return congestionLevel;
    }

    /**
     * Getter for description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 判断两个实体是否相等
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Traffic)) return false;
        Traffic traffic = (Traffic) o;
        return id != null && id.equals(traffic.id);
    }

    /**
     * 获取哈希码
     */
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}