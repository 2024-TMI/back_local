package com.example.back_local.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @PackageName : com.example.back_local.dto.group
 * @FileName : GroupCreateDto
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupAfterCreateDto {
    private String username;
    private Long group_id;
    private String invite_code;
    private String group_name;
    private String group_category;
    private Long total;
    private Long user_group_mapping_id;
    private String group_role;
}
