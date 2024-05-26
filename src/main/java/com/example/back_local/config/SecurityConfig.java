package com.example.back_local.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @PackageName : com.example.back_local.config
 * @FileName : SecurityConfig
 * @Author : noglass_gongdae
 * @Date : 2024-05-26
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //csrf disable
        http
            .csrf((auth) -> auth.disable());
        http
            .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));

        //Form 로그인 방식 disable
        http
            .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
            .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/ecom/api/swagger").permitAll()
                .requestMatchers("/kakao/login").permitAll()
                .anyRequest().authenticated());

        //세션 설정 - jwt 방식에선 사용하지 않음
        http
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
