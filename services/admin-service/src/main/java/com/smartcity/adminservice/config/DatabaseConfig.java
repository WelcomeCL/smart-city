package com.smartcity.adminservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据库配置类
 * 配置JPA和数据源集成
 */
@Configuration
@EnableJpaRepositories("com.smartcity.adminservice.mapper")
@EntityScan("com.smartcity.adminservice.entity")
@EnableTransactionManagement
public class DatabaseConfig {
    // 数据库配置通过application.yml和nacos配置文件自动配置
    // 此配置类用于明确指定扫描路径和启用事务管理
}
