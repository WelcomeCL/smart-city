package com.smartcity.traffic.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 交通信息聚合根
 * 表示城市中的交通状况信息
 */
@Getter
public class Traffic {
    
    private Long id;
    private String location;
    private TrafficStatus status;
    private Integer congestionLevel; // 1-5, 1表示畅通，5表示严重拥堵
    private Integer averageSpeed; // 平均车速(km/h)
    private String description;
    private LocalDateTime updateTime;
    
    /**
     * 构造函数，创建新的交通信息
     */
    public Traffic(Long id, String location, TrafficStatus status, Integer congestionLevel, Integer averageSpeed, String description) {
        // 由于不再继承Entity类，我们需要自己处理id字段
        this.id = id;
        this.location = location;
        this.status = status;
        this.congestionLevel = congestionLevel;
        this.averageSpeed = averageSpeed;
        this.description = description;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 更新交通状态
     */
    public void updateStatus(TrafficStatus status, Integer congestionLevel, Integer averageSpeed) {
        this.status = status;
        this.congestionLevel = congestionLevel;
        this.averageSpeed = averageSpeed;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 更新交通描述
     */
    public void updateDescription(String description) {
        this.description = description;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 标记为拥堵
     */
    public void markAsCongested(Integer congestionLevel, String reason) {
        this.status = TrafficStatus.CONGESTED;
        this.congestionLevel = congestionLevel;
        this.description = reason;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 标记为畅通
     */
    public void markAsClear() {
        this.status = TrafficStatus.CLEAR;
        this.congestionLevel = 1;
        this.description = "交通畅通";
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 交通状态枚举
     */
    public enum TrafficStatus {
        CLEAR,        // 畅通
        MODERATE,     // 一般
        CONGESTED,    // 拥堵
        HEAVY,        // 严重拥堵
        CLOSED        // 封闭
    }
}