package com.example.accommodationservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)
@Configuration
class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(
            @Value("${application-description}") String appDescription,
            @Value("${application-version}") String appVersion
    ){
        return new OpenAPI()
                .addServersItem(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8080")
                )
                .info(new Info()
                        .title("Product catalog service")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

}
