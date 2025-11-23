package com.smartcity.traffic.domain.service;

import com.smartcity.traffic.domain.Traffic;
import com.smartcity.traffic.domain.Traffic.TrafficStatus;
import com.smartcity.traffic.domain.repository.TrafficRepository;

import java.util.List;
import java.util.Optional;

/**
 * 交通领域服务
 * 封装交通信息管理的业务逻辑
 */
public class TrafficDomainService {
    
    private final TrafficRepository trafficRepository;
    
    public TrafficDomainService(TrafficRepository trafficRepository) {
        this.trafficRepository = trafficRepository;
    }
    
    /**
     * 创建新的交通信息
     */
    public Traffic createTraffic(String location, Integer congestionLevel, String description) {
        // 检查位置是否已存在
        if (trafficRepository.existsByLocation(location)) {
            throw new IllegalArgumentException("Traffic information already exists at this location: " + location);
        }
        
        // 根据拥堵级别确定初始状态
        TrafficStatus status = determineInitialStatus(congestionLevel);
        
        // 创建交通实体
        Traffic traffic = new Traffic(location, status, congestionLevel, description);
        return trafficRepository.save(traffic);
    }
    
    /**
     * 根据拥堵级别确定初始状态
     */
    private TrafficStatus determineInitialStatus(Integer congestionLevel) {
        if (congestionLevel <= 3) {
            return TrafficStatus.NORMAL;
        } else if (congestionLevel <= 7) {
            return TrafficStatus.CONGESTED;
        } else {
            return TrafficStatus.BLOCKED;
        }
    }
    
    /**
     * 更新交通状态
     */
    public Traffic updateTrafficStatus(Long trafficId, TrafficStatus status) {
        Optional<Traffic> trafficOptional = trafficRepository.findById(trafficId);
        if (trafficOptional.isEmpty()) {
            throw new IllegalArgumentException("Traffic information not found with id: " + trafficId);
        }
        
        Traffic traffic = trafficOptional.get();
        traffic.updateStatus(status);
        return trafficRepository.save(traffic);
    }
    
    /**
     * 更新拥堵级别
     */
    public Traffic updateCongestionLevel(Long trafficId, Integer congestionLevel) {
        Optional<Traffic> trafficOptional = trafficRepository.findById(trafficId);
        if (trafficOptional.isEmpty()) {
            throw new IllegalArgumentException("Traffic information not found with id: " + trafficId);
        }
        
        Traffic traffic = trafficOptional.get();
        traffic.updateCongestionLevel(congestionLevel);
        return trafficRepository.save(traffic);
    }
    
    /**
     * 查找拥堵路段
     */
    public List<Traffic> findCongestedRoads(Integer threshold) {
        return trafficRepository.findByCongestionLevelGreaterThanEqual(threshold);
    }
    
    /**
     * 获取所有交通信息
     */
    public List<Traffic> getAllTrafficInfo() {
        return trafficRepository.findAll();
    }
    
    /**
     * 根据位置查找交通信息
     */
    public Optional<Traffic> findTrafficByLocation(String location) {
        return trafficRepository.findByLocation(location);
    }
    
    /**
     * 删除交通信息
     */
    public void deleteTraffic(Long trafficId) {
        if (!trafficRepository.existsById(trafficId)) {
            throw new IllegalArgumentException("Traffic information not found with id: " + trafficId);
        }
        trafficRepository.deleteById(trafficId);
    }
}