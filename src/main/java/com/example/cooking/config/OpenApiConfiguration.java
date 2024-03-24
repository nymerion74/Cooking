package com.example.cooking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * class used for Swagger
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    /**
     * this method add bearer token in "Authorize" section in swagger page
     */
    public OpenApiCustomizer securityCustomizer() {
        return openApi -> openApi
                .components(new Components().addSecuritySchemes("Bearer Token",
                                                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Token"));
    }
}
