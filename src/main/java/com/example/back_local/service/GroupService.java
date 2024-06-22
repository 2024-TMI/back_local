package com.example.back_local.service;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.entity.GroupEntity;
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

    public GroupAfterCreateDto createGroup(String group_name, String type);

    public List<GroupEntity> getGroupLists();

    public Boolean removeGroup(Long group_id);

    public String checkGroupRole(Long group_id);
}
