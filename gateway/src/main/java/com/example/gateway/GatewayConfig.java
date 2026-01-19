package com.example.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator setupRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();

        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri)
                )
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(
                                c -> c
                                        .setName("circuitBreaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                        )
                        .uri(httpUri)
                )
                // trzeba zrobić serwis do autoryzacji i serwis do cruda
                .route(p -> p
                        .path("/api/v1/**")
                        .uri("")
                )
                .route(p -> p
                        .path(" /v3/api-docs/**")
                        .uri("")
                )
                .route(p -> p
                        .path("/swagger-ui/**")
                        .uri("")
                )
                .route(p -> p
                        .path(" /swagger-ui.html")
                        .uri("")
                )
                .build();
    }

}
