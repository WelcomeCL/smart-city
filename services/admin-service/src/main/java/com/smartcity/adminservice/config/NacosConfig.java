package com.smartcity.adminservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * Nacos配置类
 * 配置Nacos服务发现和配置中心集成
 */
@Configuration
@EnableDiscoveryClient
public class NacosConfig {
    // 服务发现和配置中心集成通过@EnableDiscoveryClient注解自动配置
    // 详细配置已在bootstrap.yml和nacos配置文件中设置
}
