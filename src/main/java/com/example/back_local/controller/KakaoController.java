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
        LOGGER.info("accessToken : {}", accessToken);

        UserDto memberInfo = kakaoService.kakaoLoginOrRegister(accessToken);
        if(memberInfo != null){
            String token = jwtUtil.createJwt(memberInfo.getUsername(), memberInfo.getRole(), 60*60*60L);
            Cookie cookie = createCookie("Authorization", token);
            response.addCookie(cookie);
            LOGGER.info("----------------kakoLogin End----------------");
            return ResponseEntity.ok().body(memberInfo);
        } else {
            LOGGER.info("----------------kakoLogin End----------------");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    public Cookie createCookie(String key, String value){
        LOGGER.info("------------------createCookie------------------");
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60); // 쿠키 수명 설정
        //cookie.setSecure(true); //https 프로토콜을 통해서만 전송
        cookie.setPath("/"); // 해당 경로와 그 이하 경로에서 사용 가능
        cookie.setHttpOnly(true); //javaScript에서 해당 쿠키에 액세스 불가능 -> XSS 공격 대비 가능
        LOGGER.info("------------------createCookie End------------------");
        return cookie;
    }
}
