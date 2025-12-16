package com.mitienda.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                
                // Publica Store
                registry.addMapping("/api/v1/store/**") // !! Aplica a todos los endpoint de store
                        .allowedOrigins("http://localhost:5173") // :5173 react-vite
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("*"); // !!! permite tokens, cabeceras

                // Publica Recommendations
                registry.addMapping("/api/v1/recommendations/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("POST", "OPTIONS")
                        .allowedHeaders("*");

                // Privada Admin
                registry.addMapping("/api/v1/admin/**") // Aplica a admin
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
                        // .allowCredentials(true);

            }
        };
    }
}