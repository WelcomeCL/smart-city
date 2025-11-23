package com.smartcity.traffic.domain.model;

import com.smartcity.traffic.domain.entity.Traffic;
import com.smartcity.traffic.domain.entity.Traffic.TrafficStatus;
import java.util.List;
import java.util.Optional;

/**
 * 交通信息仓储接口
 * 定义交通信息的数据访问方法
 */
public interface TrafficRepository {
    
    /**
     * 保存交通信息
     */
    Traffic save(Traffic traffic);
    
    /**
     * 根据ID查找交通信息
     */
    Optional<Traffic> findById(Long id);
    
    /**
     * 根据ID删除交通信息
     */
    void deleteById(Long id);
    
    /**
     * 检查ID是否存在
     */
    boolean existsById(Long id);
    
    /**
     * 根据位置查找交通信息
     */
    Optional<Traffic> findByLocation(String location);
    
    /**
     * 根据状态查找交通信息
     */
    List<Traffic> findByStatus(TrafficStatus status);
    
    /**
     * 查找拥堵级别大于等于指定值的交通信息
     */
    List<Traffic> findByCongestionLevelGreaterThanEqual(Integer congestionLevel);
    
    /**
     * 获取所有交通信息
     */
    List<Traffic> findAll();
    
    /**
     * 检查指定位置是否已存在交通信息
     */
    boolean existsByLocation(String location);
}