package com.example.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties
public class UriConfiguration {
    private String httpbin = "http://localhost:80";

    public String getHttpbin(){
        return httpbin;
    }

    public void setHttpbin(String httpbin){
        this.httpbin = httpbin;
    }
}
