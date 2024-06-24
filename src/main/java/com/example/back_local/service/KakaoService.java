package com.example.back_local.service;

import com.example.back_local.dto.user.UserDto;

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
