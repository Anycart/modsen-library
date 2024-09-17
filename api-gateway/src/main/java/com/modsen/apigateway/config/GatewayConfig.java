package com.modsen.apigateway.config;

import com.modsen.apigateway.jwt.JwtFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    private final JwtFilter filter;

    public GatewayConfig(JwtFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("books-service", r -> r.path("/api/books/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://books-service"))
                .route("books-service", r -> r.path("/api/authors/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://books-service"))
                .route("library-service", r -> r.path("/api/library/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://library-service"))
                .build();
    }
}
