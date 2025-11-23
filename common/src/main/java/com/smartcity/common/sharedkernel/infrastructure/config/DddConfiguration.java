package com.smartcity.common.sharedkernel.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * DDD基础设施配置类
 * 提供DDD相关的共享配置和Bean定义
 */
@Configuration
public class DddConfiguration {
    
    /**
     * 领域事件发布者配置
     * 在实际应用中，这里可以配置事件发布相关的组件
     */
    @Bean
    @ConditionalOnMissingBean
    public DomainEventPublisher domainEventPublisher() {
        // 默认实现，实际项目中可以替换为基于消息队列的实现
        return new SimpleDomainEventPublisher();
    }
    
    /**
     * 简单的领域事件发布者实现
     */
    public static class SimpleDomainEventPublisher implements DomainEventPublisher {
        
        @Override
        public void publish(Object event) {
            // 默认实现，仅记录日志
            System.out.println("Publishing domain event: " + event.getClass().getName());
        }
    }
    
    /**
     * 领域事件发布者接口
     */
    public interface DomainEventPublisher {
        void publish(Object event);
    }
}