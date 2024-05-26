package com.example.back_local.service.impl;

import com.example.back_local.dto.KakaoUserInfoDto;
import com.example.back_local.dto.UserDto;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.jwt.JWTUtil;
import com.example.back_local.repository.UserRepository;
import com.example.back_local.service.KakaoService;
import jakarta.servlet.http.Cookie;
import java.net.URI;
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
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);
        if(kakaoUserInfoDto != null){
            UserEntity member = saveOrReturnUser(makeUserEntity(kakaoUserInfoDto));

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
    @Override
    public Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60); // 쿠키 수명 설정
        //cookie.setSecure(true); //https 프로토콜을 통해서만 전송
        cookie.setPath("/"); // 해당 경로와 그 이하 경로에서 사용 가능
        cookie.setHttpOnly(true); //javaScript에서 해당 쿠키에 액세스 불가능 -> XSS 공격 대비 가능
        return cookie;
    }

    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) {
        LOGGER.info("---------------getKakaoUserInfo--------------");
        LOGGER.info("-------------getKakaoUserInfo End------------");
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
        String username = "kakao_" + kakaoUserInfoDto.getId().toString();
        String nickname = kakaoUserInfoDto.getProperties().get("nickname").toString();
        String email = kakaoUserInfoDto.getProperties().get("email").toString();
        String role = "ROLE_USER";
        String profile_image = kakaoUserInfoDto.getProperties().get("profile_image").toString();
        String thumbnail_image = kakaoUserInfoDto.getProperties().get("thumbnail_image").toString();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        String provider = "kakao";
        String providerId = kakaoUserInfoDto.getId().toString();

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
            .build();
    }

    public UserEntity saveOrReturnUser(UserEntity user){
        Optional<UserEntity> findUser = userRepository.findByUsername(user.getUsername());
        return findUser.orElse(userRepository.save(user));
    }
}
