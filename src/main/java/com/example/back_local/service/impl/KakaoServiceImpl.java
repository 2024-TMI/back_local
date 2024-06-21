package com.example.back_local.service.impl;

import com.example.back_local.dto.KakaoUserInfoDto;
import com.example.back_local.dto.UserDto;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.example.back_local.jwt.JWTUtil;
import com.example.back_local.repository.UserRepository;
import com.example.back_local.service.KakaoService;
import jakarta.servlet.http.Cookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private Logger LOGGER = LoggerFactory.getLogger(KakaoServiceImpl.class);
    @Value("${password.encode.value}")
    private String password;

    @Override
    public UserDto kakaoLoginOrRegister(String accessToken) {
        LOGGER.info("---------------kakaoLoginOrRegister--------------");
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);
        if(kakaoUserInfoDto != null){
            UserEntity member = saveOrReturnUser(makeUserEntity(kakaoUserInfoDto));

            LOGGER.info("---------------kakaoLoginOrRegister end--------------");
            return UserDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .profile_image(member.getProfile_image())
                .thumbnail_image(member.getThumbnail_image())
                .role(member.getRole())
                .build();
        }
        return null;
    }

    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) {
        LOGGER.info("---------------getKakaoUserInfo--------------");
        String baseUrl = "https://kapi.kakao.com";
        String path = "/v2/user/me";

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
                LOGGER.info("---------------getKakaoUserInfo End--------------");
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

    public UserEntity makeUserEntity(KakaoUserInfoDto kakaoUserInfoDto){
        LOGGER.info("---------------makeUserEntity--------------");
        String username = "kakao_" + kakaoUserInfoDto.getId().toString();
        String nickname = kakaoUserInfoDto.getProperties().get("nickname").toString();
        String email = null;
        String role = "ROLE_USER";
        String profile_image = kakaoUserInfoDto.getProperties().get("profile_image").toString();
        String thumbnail_image = kakaoUserInfoDto.getProperties().get("thumbnail_image").toString();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        String provider = "kakao";
        String providerId = kakaoUserInfoDto.getId().toString();
        List<UserGroupMappingEntity> list = new ArrayList<>();

        LOGGER.info("---------------makeUserEntity End--------------");
        return UserEntity.builder()
            .username(username)
            .nickname(nickname)
            .email(email)
            .role(role)
            .profile_image(profile_image)
            .thumbnail_image(thumbnail_image)
            .password(encodedPassword)
            .provider(provider)
            .providerId(providerId)
                .userGroupMappings(list)
            .build();
    }

    public UserEntity saveOrReturnUser(UserEntity user){
        LOGGER.info("---------------saveOrReturnUser--------------");
        Optional<UserEntity> findUser = userRepository.findByUsername(user.getUsername());
        LOGGER.info("---------------saveOrReturnUser end--------------");
        return findUser.orElse(userRepository.save(user));
    }
}
