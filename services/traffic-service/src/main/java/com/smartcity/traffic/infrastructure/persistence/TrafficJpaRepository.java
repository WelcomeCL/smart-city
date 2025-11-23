package com.smartcity.traffic.infrastructure.persistence;

import com.smartcity.traffic.infrastructure.persistence.jpa.TrafficJpaEntity;
import com.smartcity.traffic.domain.Traffic.TrafficStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA交通信息仓储接口
 * 提供与数据库交互的方法
 */
@Repository
public interface TrafficJpaRepository extends JpaRepository<TrafficJpaEntity, Long> {
    
    /**
     * 根据位置查找交通信息
     */
    Optional<TrafficJpaEntity> findByLocation(String location);
    
    /**
     * 根据状态查找交通信息列表
     */
    List<TrafficJpaEntity> findByStatus(TrafficStatus status);
    
    /**
     * 根据拥堵级别查找交通信息列表
     */
    List<TrafficJpaEntity> findByCongestionLevelGreaterThanEqual(Integer congestionLevel);
    
    /**
     * 检查位置是否已存在
     */
    boolean existsByLocation(String location);
}