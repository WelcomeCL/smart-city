package com.smartcity.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * API网关路由配置
 * 配置从网关到各个微服务的路由规则
 * 适配DDD架构的微服务接口
 */
@Configuration
public class RouteConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 用户服务路由配置
                .route("user-service-route", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .rewritePath("/api/users/(?<segment>.*)", "/api/user/${segment}")
                                .addResponseHeader("X-Response-Time", "${responseTime}")
                        )
                        .uri("lb://user-service")
                )
                
                // 交通服务路由配置
                .route("traffic-service-route", r -> r
                        .path("/api/traffic/**")
                        .filters(f -> f
                                .addResponseHeader("X-Response-Time", "${responseTime}")
                        )
                        .uri("lb://traffic-service")
                )
                
                // 健康检查路由
                .route("health-check", r -> r
                        .path("/health")
                        .uri("forward:/actuator/health")
                )
                
                .build();
    }
}