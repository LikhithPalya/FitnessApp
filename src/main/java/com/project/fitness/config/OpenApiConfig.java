package com.project.fitness.config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

import static org.springframework.web.servlet.function.RequestPredicates.version;

public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fitness tracking API"))
                        .version("v1.0")
                        .description("production grade API's")
    }
}
