package com.smartcity.common.sharedkernel.domain.model;

import java.util.Optional;

/**
 * 仓储接口基类
 * 用于访问领域对象的持久化数据
 * 
 * @param <T> 实体类型
 * @param <ID> 实体ID类型
 */
public interface Repository<T extends Entity<ID>, ID> {
    
    /**
     * 保存实体
     */
    T save(T entity);
    
    /**
     * 根据ID查找实体
     */
    Optional<T> findById(ID id);
    
    /**
     * 删除实体
     */
    void delete(T entity);
    
    /**
     * 根据ID删除实体
     */
    void deleteById(ID id);
    
    /**
     * 检查实体是否存在
     */
    boolean existsById(ID id);
}