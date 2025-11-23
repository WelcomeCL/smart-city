package com.smartcity.traffic.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * 交通信息JPA仓储接口
 * 提供与数据库交互的方法
 */
@Repository
public interface TrafficJpaRepository extends JpaRepository<TrafficJpaEntity, Long> {

    /**
     * 根据位置查找交通信息
     * @param location 位置信息
     * @return 交通信息实体
     */
    Optional<TrafficJpaEntity> findByLocation(String location);

    /**
     * 根据状态查找交通信息
     * @param status 交通状态
     * @return 交通信息列表
     */
    List<TrafficJpaEntity> findByStatus(String status);

    /**
     * 查找拥堵级别大于等于指定值的交通信息
     * @param congestionLevel 拥堵级别
     * @return 交通信息列表
     */
    List<TrafficJpaEntity> findByCongestionLevelGreaterThanEqual(Integer congestionLevel);

    /**
     * 检查指定位置是否已存在交通信息
     * @param location 位置信息
     * @return 是否存在
     */
    boolean existsByLocation(String location);
}