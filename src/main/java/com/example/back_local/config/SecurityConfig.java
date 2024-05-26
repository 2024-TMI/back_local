package com.example.back_local.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

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

    @Value("${cors.client.url}")
    private String client_URL;

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

        http
            .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(Collections.singletonList(client_URL));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true); //서버가 클라이언트에게 인증된 사용자 정보를 전달할 수 있는지 여부를 결정
                    configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
                    configuration.setMaxAge(3600L);
                    //서버에서 클라이언트로 반환될 때 노출되는 헤더를 설정하는 부분
                    configuration.setExposedHeaders(Collections.singletonList("Set-Cookie")); //쿠키를 설정할 때 사용
                    configuration.setExposedHeaders(Collections.singletonList("Authorization")); //인증된 요청을 할 때 사용
                    //서버로부터 반환되는 응답에 있는 헤더들을 접근할 수 있게됨.
                    return configuration;
                }
            }));

        //세션 설정 - jwt 방식에선 사용하지 않음
        http
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
