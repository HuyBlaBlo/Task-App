package com.huybla.tasks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // turn on CORS for all endpoint
        registry.addMapping("/**")
                // allowd all port like 5500 live server, 3000 react
                .allowedOrigins("*")
                // allow method
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // allow every inf in Header
                .allowedHeaders("*");
    }
}
