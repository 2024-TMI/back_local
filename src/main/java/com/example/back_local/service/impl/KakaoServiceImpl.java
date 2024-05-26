package com.example.back_local.service.impl;

import com.example.back_local.dto.KakaoUserInfoDto;
import com.example.back_local.dto.UserDto;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.service.KakaoService;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @PackageName : com.example.back_local.service.impl
 * @FileName : KakaoServiceImpl
 * @Author : noglass_gongdae
 * @Date : 2024-05-23
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
public class KakaoServiceImpl implements KakaoService {

    private Logger LOGGER = LoggerFactory.getLogger(KakaoServiceImpl.class);
    private String baseUrl;
    private String path;

    @Override
    public void kakaoLoginOrRegister(String accessToken) {

        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);

    }

    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) {
        LOGGER.info("---------------getKakaoUserInfo--------------");
        LOGGER.info("-------------getKakaoUserInfo End------------");
        baseUrl = "https://kapi.kakao.com";
        path = "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);

        URI uri = UriComponentsBuilder
            .fromUriString(baseUrl)
            .path(path)
            .encode().build().toUri();

        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<KakaoUserInfoDto> responseEntity = restTemplate.postForEntity(uri, httpEntity, KakaoUserInfoDto.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                LOGGER.info("responseEntity.getBody() : {}", responseEntity.getBody());
                return responseEntity.getBody();
            }
            else{
                LOGGER.info("Failed Response UserInfo from Kakao, statusCode : {}", responseEntity.getStatusCode());
                return null;
            }
        }catch (Exception e) {
            LOGGER.info("Failed Response UserInfo from Kakao");
            return null;
        }
    }

    public void findUser(UserEntity userEntity){

    }
}
