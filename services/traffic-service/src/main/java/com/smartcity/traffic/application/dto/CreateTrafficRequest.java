package com.smartcity.traffic.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 创建交通信息请求DTO
 * 用于接收创建交通信息的请求参数
 */
public class CreateTrafficRequest {
    
    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;
    
    @NotNull(message = "Congestion level is required")
    private Integer congestionLevel;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    // Getter methods
    public String getLocation() {
        return location;
    }
    
    public Integer getCongestionLevel() {
        return congestionLevel;
    }
    
    public String getDescription() {
        return description;
    }
}