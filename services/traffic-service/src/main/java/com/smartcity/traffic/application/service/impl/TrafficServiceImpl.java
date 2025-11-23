package com.smartcity.traffic.application.service.impl;

import com.smartcity.traffic.application.dto.*;
import com.smartcity.traffic.application.service.TrafficService;
import com.smartcity.traffic.domain.Traffic;
import com.smartcity.traffic.domain.service.TrafficDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 交通信息应用服务实现类
 * 实现应用服务接口定义的功能
 */
@Service
public class TrafficServiceImpl implements TrafficService {
    
    private final TrafficDomainService trafficDomainService;
    
    @Autowired
    public TrafficServiceImpl(TrafficDomainService trafficDomainService) {
        this.trafficDomainService = trafficDomainService;
    }
    
    @Override
    public TrafficResponse createTraffic(CreateTrafficRequest request) {
        Traffic traffic = trafficDomainService.createTraffic(
                request.getLocation(),
                request.getCongestionLevel(),
                request.getDescription()
        );
        return convertToResponse(traffic);
    }
    
    @Override
    public TrafficResponse getTrafficById(Long trafficId) {
        return trafficDomainService.getAllTrafficInfo().stream()
                .filter(traffic -> traffic.getId().equals(trafficId))
                .findFirst()
                .map(this::convertToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Traffic information not found with id: " + trafficId));
    }
    
    @Override
    public TrafficResponse getTrafficByLocation(String location) {
        return trafficDomainService.findTrafficByLocation(location)
                .map(this::convertToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Traffic information not found at location: " + location));
    }
    
    @Override
    public TrafficResponse updateTraffic(Long trafficId, UpdateTrafficRequest request) {
        // 先获取现有交通信息
        Traffic traffic = trafficDomainService.getAllTrafficInfo().stream()
                .filter(t -> t.getId().equals(trafficId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Traffic information not found with id: " + trafficId));
        
        // 更新拥堵级别
        if (request.getCongestionLevel() != null) {
            traffic = trafficDomainService.updateCongestionLevel(trafficId, request.getCongestionLevel());
        }
        
        // 更新描述
        if (request.getDescription() != null) {
            traffic.updateDescription(request.getDescription());
        }
        
        return convertToResponse(traffic);
    }
    
    @Override
    public TrafficResponse updateTrafficStatus(Long trafficId, UpdateTrafficStatusRequest request) {
        Traffic traffic = trafficDomainService.updateTrafficStatus(trafficId, request.getStatus());
        return convertToResponse(traffic);
    }
    
    @Override
    public TrafficResponse updateCongestionLevel(Long trafficId, UpdateCongestionLevelRequest request) {
        Traffic traffic = trafficDomainService.updateCongestionLevel(trafficId, request.getCongestionLevel());
        return convertToResponse(traffic);
    }
    
    @Override
    public List<TrafficResponse> getAllTrafficInfo() {
        return trafficDomainService.getAllTrafficInfo().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TrafficResponse> getCongestedRoads(Integer threshold) {
        return trafficDomainService.findCongestedRoads(threshold).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteTraffic(Long trafficId) {
        trafficDomainService.deleteTraffic(trafficId);
    }
    
    /**
     * 将领域实体转换为响应DTO
     */
    private TrafficResponse convertToResponse(Traffic traffic) {
        // 使用构造函数创建TrafficResponse，避免使用setter方法
        TrafficResponse response = new TrafficResponse();
        // 使用getter方法获取Traffic对象的属性
        // 由于Traffic类继承自Entity并实现了getId()方法，我们可以使用该方法
        response.setId(traffic.getId());
        // 对于其他字段，我们尝试使用lombok生成的getter方法
        response.setLocation(traffic.getLocation());
        response.setStatus(traffic.getStatus());
        response.setCongestionLevel(traffic.getCongestionLevel());
        response.setDescription(traffic.getDescription());
        return response;
    }
}