package com.example.back_local.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @PackageName : com.example.back_local.config
 * @FileName : CorsMvcConfig
 * @Author : noglass_gongdae
 * @Date : 2024-05-25
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Value("${cors.client.url}")
    private String client_URL;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .exposedHeaders("Authorization")
            .allowedOrigins(client_URL);
    }
}
