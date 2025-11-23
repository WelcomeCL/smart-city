package com.smartcity.traffic.application.dto;

import jakarta.validation.constraints.Size;

/**
 * 更新交通信息请求DTO
 * 用于接收更新交通信息的请求参数
 */
public class UpdateTrafficRequest {
    
    private Integer congestionLevel;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    // Getter methods
    public Integer getCongestionLevel() {
        return congestionLevel;
    }
    
    public String getDescription() {
        return description;
    }
}