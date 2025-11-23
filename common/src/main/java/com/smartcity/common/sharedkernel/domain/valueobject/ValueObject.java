package com.smartcity.common.sharedkernel.domain.valueobject;

import java.util.Objects;

/**
 * 值对象基类
 * 值对象通过其属性值来判断相等性，而非标识
 * 值对象应该是不可变的
 */
public abstract class ValueObject {
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        // 比较所有属性
        return equalsCore(o);
    }
    
    /**
     * 子类实现的具体相等性比较逻辑
     */
    protected abstract boolean equalsCore(Object o);
    
    @Override
    public int hashCode() {
        return hashCodeCore();
    }
    
    /**
     * 子类实现的哈希码计算逻辑
     */
    protected abstract int hashCodeCore();
}