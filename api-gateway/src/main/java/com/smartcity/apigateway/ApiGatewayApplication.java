package com.smartcity.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;

/**
 * API网关应用入口
 * 适配DDD架构的微服务系统
 */
@SpringBootApplication
@EnableDiscoveryClient // 启用服务发现，支持负载均衡路由
public class ApiGatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
    
    /**
     * 启用路由自动刷新
     */
    @Bean
    public RouteDefinitionLocator routeDefinitionLocator(RouteDefinitionRepository repository) {
        return repository;
    }
}