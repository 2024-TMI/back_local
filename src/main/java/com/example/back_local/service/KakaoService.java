package com.example.back_local.service;

import com.example.back_local.dto.UserDto;
import com.example.back_local.entity.UserEntity;
import jakarta.servlet.http.Cookie;

/**
 * @PackageName : com.example.back_local.service
 * @FileName : KakaoService
 * @Author : noglass_gongdae
 * @Date : 2024-05-23
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface KakaoService {

    public UserDto kakaoLoginOrRegister(String accessToken);
}
