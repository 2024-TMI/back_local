package com.example.back_local.generation;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.dto.group.GroupConfigurationDto;
import com.example.back_local.dto.group.GroupConfigurationUsersDto;
import com.example.back_local.dto.group.GroupListDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.example.back_local.repository.UserGroupMappingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MakeDto{

    private final UserGroupMappingRepository userGroupMappingRepository;

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

    public List<GroupListDto> makeGroupList(List<GroupEntity> groupEntities){
//        if(groupEntities.isEmpty()){
//            return null;
//        }
        return groupEntities.stream().map(groupEntity ->
            GroupListDto.builder()
                .group_category(groupEntity.getGroup_category())
                .group_id(groupEntity.getId())
                .group_name(groupEntity.getGroup_name())
                .total(groupEntity.getTotal())
                .build()).toList();
    }

    public GroupConfigurationDto makeGCDto(GroupEntity groupEntity, List<GroupConfigurationUsersDto> usersDtoList){
        String section1 = groupEntity.getSection().getSection1();
        String section2 = groupEntity.getSection().getSection2();
        List<String> section = new ArrayList<>();
        section.add(section1);
        section.add(section2);

        return GroupConfigurationDto.builder()
            .group_id(groupEntity.getId())
            .group_name(groupEntity.getGroup_name())
            .section(section)
            .users(usersDtoList)
            .build();
    }

    public List<GroupConfigurationUsersDto> makeGCUDto(List<UserEntity> userEntities, Long group_id){

        return userEntities.stream().map(userEntity -> GroupConfigurationUsersDto.builder()
            .nickname(userEntity.getNickname())
            .profile_image(userEntity.getProfile_image())
            .group_role(userGroupMappingRepository.findGroupRoleByUsernameAndGroupId(userEntity.getUsername(), group_id))
            .build()).toList();

    }
}
