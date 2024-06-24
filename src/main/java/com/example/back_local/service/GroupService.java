package com.example.back_local.service;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.dto.group.GroupConfigurationDto;
import com.example.back_local.dto.group.GroupListDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import java.util.List;

/**
 * @PackageName : com.example.back_local.service
 * @FileName : GroupService
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface GroupService {

    GroupAfterCreateDto createGroup(String group_name, String type);

    List<GroupListDto> getGroupLists();

    Boolean removeGroup(Long group_id);

    String checkGroupRole(Long group_id);

    GroupConfigurationDto groupInfo(Long group_id);
}
