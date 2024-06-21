package com.example.back_local.jwt;

import com.example.back_local.dto.CustomUserDetails;
import com.example.back_local.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Enumeration;


public class JWTFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);
    private final JWTUtil jwtUtil;
    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI(); //로그인 요청 및 api 문서 접근 uri는 통과시키기
        if (path.equals("/ecom/api/swagger") || path.equals("/kakao/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Cookie들을 불러온 뒤, Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            LOGGER.info("cookie : {}", cookie);
//            LOGGER.info("doFilterInternal cookie.getName() : {}", cookie.getName());
//            //System.out.println(cookie.getName());
//            if (cookie.getName().equals("Authorization")) {
//                authorization = cookie.getValue();
//            }
//        }
        if(request.getHeader("Authorization") == null){
            filterChain.doFilter(request, response);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Don't exist jwt");
            return;
        }
        authorization = request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null) {
            LOGGER.info("token null");
            //System.out.println("token null");
            filterChain.doFilter(request, response);
            // 조건이 해당되면 메서드 종료(필수!)
            return;
        }

        //토큰
        String token = authorization;

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            LOGGER.info("token expired");
            //System.out.println("token expired");
            filterChain.doFilter(request, response);
            // 조건이 해당되면 메서드 종료(필수!)
            return;
        }

        LOGGER.info("Good State Token");

        //토큰에서 username, role 획득
        String username = jwtUtil.getUsername(token);
        //LOGGER.info("username : {}", username);
        String role = jwtUtil.getRole(token);
        //LOGGER.info("role : {}", role);

        //userDto를 생성하여 값 set
        UserDto userDto = UserDto.builder()
                .username(username)
                .role(role)
                .build();

        //userDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userDto);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
