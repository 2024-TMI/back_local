package com.example.back_local.dto.group;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @PackageName : com.example.back_local.dto.group
 * @FileName : GroupConfigurationDto
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
public class GroupConfigurationDto {

    // 1. 그룹 id
    // 2. 그룹 명
    // 3. section 리스트
    // 4. 팅원 정보 - 이름, 프로필 사진

    private Long group_id;
    private String group_name;
    private List<String> section;
    private List<GroupConfigurationUsersDto> users;
}
