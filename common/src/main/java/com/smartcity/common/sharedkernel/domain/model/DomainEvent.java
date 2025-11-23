package com.smartcity.common.sharedkernel.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * 领域事件基类
 * 表示领域中发生的重要事件
 */
public abstract class DomainEvent {
    
    private final String eventId;
    private final Instant occurredOn;
    
    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.occurredOn = Instant.now();
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public Instant getOccurredOn() {
        return occurredOn;
    }
}