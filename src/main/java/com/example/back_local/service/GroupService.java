package com.example.back_local.service;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.entity.UserGroupMappingEntity;

/**
 * @PackageName : com.example.back_local.service
 * @FileName : GroupService
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface GroupService {

    public GroupAfterCreateDto createGroup(String group_name, String type);

}
