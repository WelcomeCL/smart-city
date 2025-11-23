package com.smartcity.common.sharedkernel.domain.model;

import java.util.Objects;

/**
 * 实体基类，所有领域实体的基础类
 * 实体通过ID来标识，而不是其属性
 * 
 * @param <ID> 实体ID类型
 */
public abstract class Entity<ID> {
    
    private final ID id;
    
    protected Entity(ID id) {
        this.id = Objects.requireNonNull(id, "实体ID不能为空");
    }
    
    public ID getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}