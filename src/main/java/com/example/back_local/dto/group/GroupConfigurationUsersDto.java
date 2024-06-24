package com.example.back_local.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @PackageName : com.example.back_local.dto.user
 * @FileName : GroupConfigurationUsersDto
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
public class GroupConfigurationUsersDto {

    private String nickname;
    private String profile_image;
    private String group_role;

}
