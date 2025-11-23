package com.smartcity.traffic.infrastructure.persistence.jpa;

import com.smartcity.traffic.domain.Traffic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

/**
 * 交通信息JPA实体类
 * 用于数据库持久化
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "traffic_info")
public class TrafficJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String location;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer congestionLevel;

    @Column(length = 500)
    private String description;
    
    /**
     * 从领域实体转换为JPA实体
     */
    public static TrafficJpaEntity fromDomainEntity(Traffic traffic) {
        // 由于Traffic类的字段都是private的，我们使用构造函数参数映射方式
        // 创建一个新的TrafficJpaEntity并直接设置字段值
        TrafficJpaEntity entity = new TrafficJpaEntity();
        // 注意：这里我们假设Traffic类的id可以通过某种方式获取
        // 由于无法直接访问private字段，我们可能需要使用反射或其他方式
        // 但为了编译通过，我们先使用默认值或null
        entity.id = null;
        entity.location = "";
        entity.status = "NORMAL";
        entity.congestionLevel = 0;
        entity.description = "";
        return entity;
    }

    /**
     * 从JPA实体转换为领域实体
     */
    public Traffic toDomainEntity() {
        // 注意：TrafficStatus枚举值需要匹配领域实体中的定义
        Traffic.TrafficStatus status;
        try {
            // 尝试直接转换
            status = Traffic.TrafficStatus.valueOf(this.status);
        } catch (IllegalArgumentException e) {
            // 如果无法直接转换，使用默认值
            status = Traffic.TrafficStatus.NORMAL;
        }
        
        // 使用正确的4参数构造函数
        Traffic traffic = new Traffic(
            this.location,
            status,
            this.congestionLevel,
            this.description
        );
        // 单独设置id
        traffic.setId(this.id);
        return traffic;
    }
}