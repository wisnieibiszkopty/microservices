package com.example.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtWebFilter implements WebFilter {

    private final JwtService jwtService;

    // TODO add rest of whitelist
    private List<String> allowedPaths = List.of(
            "/api/v1/auth",
            ""
    );

    @Autowired
    public JwtWebFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (allowedPaths.stream().anyMatch(path::contains)) {
            return chain.filter(exchange);
        }

        String token = getToken(exchange.getRequest());

        if (token != null && jwtService.isTokenValid(token)) {
            var username = jwtService.extractUsername(token);
            var roles = jwtService.extractRoles(token);

            List<SimpleGrantedAuthority> authorities = (roles != null) ? roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList() : List.of();

            var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

            return chain.filter(exchange).contextWrite(
                    ReactiveSecurityContextHolder.withAuthentication(auth)
            );
        }

        return chain.filter(exchange);
    }

    private String getToken(ServerHttpRequest request){
        String authHeader = request.getHeaders().getFirst("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return null;
        }

        return authHeader.substring(7);
    }

}
