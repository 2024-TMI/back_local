package com.example.back_local.generation;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MakeDto{

    private final Logger LOGGER = LoggerFactory.getLogger(MakeEntity.class);

    public GroupAfterCreateDto makeGroupAfterCreateDto(UserEntity userEntity, GroupEntity groupEntity, UserGroupMappingEntity userGroupMappingEntity) {
        return GroupAfterCreateDto.builder()
                .username(userEntity.getUsername())
                .group_id(groupEntity.getId())
                .invite_code(groupEntity.getInvite_code())
                .group_name(groupEntity.getGroup_name())
                .group_category(groupEntity.getGroup_category())
                .total(groupEntity.getTotal())
                .user_group_mapping_id(userGroupMappingEntity.getId())
                .group_role(userGroupMappingEntity.getGroup_role())
                .build();
    }
}
