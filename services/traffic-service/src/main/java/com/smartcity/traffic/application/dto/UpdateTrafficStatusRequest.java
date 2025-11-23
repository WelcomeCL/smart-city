package com.smartcity.traffic.application.dto;

import com.smartcity.traffic.domain.Traffic.TrafficStatus;
import jakarta.validation.constraints.NotNull;

/**
 * 更新交通状态请求DTO
 * 用于接收更新交通状态的请求参数
 */
public class UpdateTrafficStatusRequest {
    
    @NotNull(message = "Status is required")
    private TrafficStatus status;
    
    // Getter method
    public TrafficStatus getStatus() {
        return status;
    }
}