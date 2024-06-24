package com.example.back_local.dto.account;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @PackageName : com.example.back_local.dto.account
 * @FileName : AccountListResponseDto
 * @Author : noglass_gongdae
 * @Date : 2024-06-24
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountListResponseDto {

    private String business_name;
    private Long amount;
    private String classification;
    private String category;
    private LocalDateTime date;

}
