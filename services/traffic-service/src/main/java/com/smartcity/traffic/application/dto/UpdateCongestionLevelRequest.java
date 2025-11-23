package com.smartcity.traffic.application.dto;

import jakarta.validation.constraints.NotNull;

/**
 * 更新拥堵级别请求DTO
 * 用于接收更新拥堵级别的请求参数
 */
public class UpdateCongestionLevelRequest {
    
    @NotNull(message = "Congestion level is required")
    private Integer congestionLevel;
    
    // Getter method
    public Integer getCongestionLevel() {
        return congestionLevel;
    }
}