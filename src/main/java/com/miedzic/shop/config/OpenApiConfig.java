package com.miedzic.shop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer token", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("Shop API")
                        .description("I created a backend api that included a shop service with things that coincided with all the technology I outlined in my resume.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mateusz Dziedzic")
                                .email("matim98@tlen.pl")
                                .url("https://github.com/Miedzic/shop")));
    }
}
