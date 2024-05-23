package com.example.back_local.controller;

import com.example.back_local.dto.KakaoDto;
import com.example.back_local.dto.UserDto;
import com.example.back_local.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 목표 : 해커톤 방식으로 로그인 후 Jwt 발급해 주기
    @PostMapping("/login")
    public ResponseEntity<UserDto> kakaoLogin(@RequestBody KakaoDto kakaoDto){
        LOGGER.info("------------------kakoLogin------------------");
        LOGGER.info("----------------kakoLogin End----------------");
        String code = kakaoDto.getCode();
        LOGGER.info("get Code : {}", code);
        String token = kakaoService.getKakaoToken(code);
        LOGGER.info("get Token : {}", token);
        UserDto userDto;
        if(token != null){
            userDto = kakaoService.getKakaoUserInfo(token);
            LOGGER.info("get UserDto : {}", userDto);
            if(userDto != null){
                LOGGER.info("----------------kakoLogin End----------------");
                return ResponseEntity.ok(userDto);
            }
            else {
                LOGGER.info("----------------kakoLogin End----------------");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        else{
            LOGGER.info("----------------kakoLogin End----------------");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
