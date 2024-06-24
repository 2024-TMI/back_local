package com.example.back_local.dto.account;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @PackageName : com.example.back_local.dto.account
 * @FileName : AccountAddDto
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
public class AccountAddDto {

    private Long group_id;

    private LocalDateTime date;

    private Long amount;

    private String business_name;

    private String category;

    private String classification; //지출 or 수입
}
