package com.example.back_local.controller;

import com.example.back_local.dto.UserDto;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.jwt.JWTUtil;
import com.example.back_local.service.KakaoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName : com.example.back_local.controller
 * @FileName : KakaoController
 * @Author : noglass_gongdae
 * @Date : 2024-05-23
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private Logger LOGGER = LoggerFactory.getLogger(KakaoController.class);

    private final KakaoService kakaoService;
    private final JWTUtil jwtUtil;

    // 목표 : 해커톤 방식으로 로그인 후 Jwt 발급해 주기
    @PostMapping("/login")
    public ResponseEntity<UserDto> kakaoLogin(@RequestParam("accesstoken") String accessToken, HttpServletResponse response){
        LOGGER.info("------------------kakoLogin------------------");
        LOGGER.info("----------------kakoLogin End----------------");
        LOGGER.info("accessToken : {}", accessToken);

        UserDto memberInfo = kakaoService.kakaoLoginOrRegister(accessToken);
        if(memberInfo != null){
            String token = jwtUtil.createJwt(memberInfo.getUsername(), memberInfo.getRole(), 60*60*60L);
            Cookie cookie = kakaoService.createCookie("Authorization", token);
            response.addCookie(cookie);
            return ResponseEntity.ok().body(memberInfo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
