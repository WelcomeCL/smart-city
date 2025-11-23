package com.smartcity.traffic.application.service;

import com.smartcity.traffic.application.dto.*;

import java.util.List;

/**
 * 交通信息应用服务接口
 * 定义应用层的功能契约
 * 作为领域层与表现层之间的桥梁
 */
public interface TrafficService {
    
    /**
     * 创建交通信息
     */
    TrafficResponse createTraffic(CreateTrafficRequest request);
    
    /**
     * 获取交通信息详情
     */
    TrafficResponse getTrafficById(Long trafficId);
    
    /**
     * 根据位置获取交通信息
     */
    TrafficResponse getTrafficByLocation(String location);
    
    /**
     * 更新交通信息
     */
    TrafficResponse updateTraffic(Long trafficId, UpdateTrafficRequest request);
    
    /**
     * 更新交通状态
     */
    TrafficResponse updateTrafficStatus(Long trafficId, UpdateTrafficStatusRequest request);
    
    /**
     * 更新拥堵级别
     */
    TrafficResponse updateCongestionLevel(Long trafficId, UpdateCongestionLevelRequest request);
    
    /**
     * 获取所有交通信息
     */
    List<TrafficResponse> getAllTrafficInfo();
    
    /**
     * 获取拥堵路段
     */
    List<TrafficResponse> getCongestedRoads(Integer threshold);
    
    /**
     * 删除交通信息
     */
    void deleteTraffic(Long trafficId);
}