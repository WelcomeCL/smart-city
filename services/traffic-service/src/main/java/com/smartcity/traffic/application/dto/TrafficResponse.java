package com.smartcity.traffic.application.dto;

import com.smartcity.traffic.domain.Traffic.TrafficStatus;

/**
 * 交通信息响应DTO
 * 用于向客户端返回交通信息数据
 */
public class TrafficResponse {
    
    private Long id;
    
    private String location;
    
    private TrafficStatus status;
    
    private Integer congestionLevel;
    
    private String description;
    
    // Getter and setter methods
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public TrafficStatus getStatus() {
        return status;
    }
    
    public void setStatus(TrafficStatus status) {
        this.status = status;
    }
    
    public Integer getCongestionLevel() {
        return congestionLevel;
    }
    
    public void setCongestionLevel(Integer congestionLevel) {
        this.congestionLevel = congestionLevel;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}