package com.smartcity.traffic.domain.repository;

import com.smartcity.traffic.domain.Traffic;
import java.util.List;
import java.util.Optional;

/**
     * 交通信息领域仓储接口
     */
public interface TrafficRepository {

    /**
     * 保存交通信息
     * @param traffic 交通信息
     * @return 保存后的交通信息
     */
    Traffic save(Traffic traffic);

    /**
     * 根据ID查找交通信息
     * @param id 交通信息ID
     * @return 交通信息Optional
     */
    Optional<Traffic> findById(Long id);

    /**
     * 根据ID删除交通信息
     * @param id 交通信息ID
     */
    void deleteById(Long id);

    /**
     * 检查ID是否存在
     * @param id 交通信息ID
     * @return 是否存在
     */
    boolean existsById(Long id);

    /**
     * 获取所有交通信息
     * @return 交通信息列表
     */
    List<Traffic> findAll();

    /**
     * 根据位置查找交通信息
     * @param location 位置信息
     * @return 交通信息Optional
     */
    Optional<Traffic> findByLocation(String location);

    /**
     * 根据状态查找交通信息
     * @param status 交通状态
     * @return 交通信息列表
     */
    List<Traffic> findByStatus(Traffic.TrafficStatus status);

    /**
     * 查找拥堵级别大于等于指定值的交通信息
     * @param congestionLevel 拥堵级别
     * @return 交通信息列表
     */
    List<Traffic> findByCongestionLevelGreaterThanEqual(Integer congestionLevel);

    /**
     * 检查指定位置是否已存在交通信息
     * @param location 位置信息
     * @return 是否存在
     */
    boolean existsByLocation(String location);
}