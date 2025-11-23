package com.smartcity.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 错误处理过滤器
 * 统一处理API网关层面的错误，提供标准的错误响应格式
 */
@Component
public class ErrorHandlingFilter extends AbstractGatewayFilterFactory<ErrorHandlingFilter.Config> {
    
    public ErrorHandlingFilter() {
        super(Config.class);
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            return chain.filter(exchange)
                    .onErrorResume(error -> handleError(exchange, error));
        }, Ordered.HIGHEST_PRECEDENCE);
    }
    
    private Mono<Void> handleError(ServerWebExchange exchange, Throwable error) {
        // 设置统一的错误响应状态码
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        
        // 记录错误日志
        System.err.println("Gateway error: " + error.getMessage());
        
        // 在实际应用中，可以根据不同类型的错误返回不同的响应内容
        // 这里简化处理，返回一个基本的错误信息
        String errorResponse = "{\"error\": \"Internal server error\", \"message\": \"" + 
                              error.getMessage() + "\"}";
        
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(errorResponse.getBytes())));
    }
    
    public static class Config {
        // 配置属性可以在这里定义
    }
}