package com.example.back_local.dto.kakao;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

/**
 * @PackageName : com.example.back_local.dto
 * @FileName : KakaoUserInfoDto
 * @Author : noglass_gongdae
 * @Date : 2024-05-23
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class KakaoUserInfoDto {
    private Long id;
    private Boolean has_signed_up;
    private LocalDateTime connected_at;
    private LocalDateTime synched_at;
    private Map<String, Object> properties;
    private Map<String, Object> kakao_account;
    private Map<String, Object> for_partner;
}
