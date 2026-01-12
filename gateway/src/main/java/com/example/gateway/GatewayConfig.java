package com.example.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator setupRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();

        return builder.routes()
                .route("httpbin_rule", r -> r
                        .path("/get")
                        .filters(f -> f
                                .addRequestHeader("Hello", "World")
                        )
                        .uri(httpUri)
                )
                .route("circuitbreaker_rule", r -> r
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(
                                c -> c
                                        .setName("")
                                        .setFallbackUri("forward:/fallback")
                                )
                        )
                        .uri(httpUri)
                )
                .build();
    }

}
