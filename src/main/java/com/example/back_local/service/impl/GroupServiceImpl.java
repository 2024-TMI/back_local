package com.example.back_local.service.impl;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.example.back_local.generation.GenerationThings;
import com.example.back_local.generation.MakeDto;
import com.example.back_local.generation.MakeEntity;
import com.example.back_local.repository.GroupRepository;
import com.example.back_local.repository.UserGroupMappingRepository;
import com.example.back_local.repository.UserRepository;
import com.example.back_local.security.SecurityUtil;
import com.example.back_local.service.GroupService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @PackageName : com.example.back_local.service.impl
 * @FileName : GroupServiceImpl
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GenerationThings generationThings;
    private final MakeDto makeDto;
    private final MakeEntity makeEntity;
    private final UserRepository userRepository;
    private final UserGroupMappingRepository userGroupMappingRepository;
    private final GroupRepository groupRepository;
    private final SecurityUtil securityUtil;

    private final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Override
    public GroupAfterCreateDto createGroup(String group_name, String group_category) {
        LOGGER.info("---------createGroup Start-----------");

        String invite_code = generationThings.generateInviteCode(group_name);
        GroupEntity groupEntity = makeEntity.makeGroupEntity(invite_code, group_name, group_category);
        GroupEntity saveGroup = groupRepository.save(groupEntity);

        String username = securityUtil.getCurrentUsername();
        if (username == null) {
            return null;
        }
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        if (userEntity == null) {
            return null;
        }
        UserGroupMappingEntity userGroupMappingEntity = makeEntity.makeUserGroupMappingEntity(userEntity, groupEntity);

        // add 할 필요없음 그냥 저장하면 됨 순서대로
//        userEntity.getUserGroupMappings().add(userGroupMappingEntity);
//        UserEntity saveUser = saveUserEntity(userEntity);
//
//        saveGroup.getUserGroupMappings().add(userGroupMappingEntity);
//        GroupEntity reSaveGroup = saveGroupEntity(saveGroup);

        UserGroupMappingEntity saveUserGroup = userGroupMappingRepository.save(userGroupMappingEntity);
        LOGGER.info("---------createGroup End-----------");
        return makeDto.makeGroupAfterCreateDto(userEntity, saveGroup, saveUserGroup);
    }

    @Override
    public List<GroupEntity> getGroupLists() {
        LOGGER.info("---------getGroupLists Start-----------");
        String username = securityUtil.getCurrentUsername();
        LOGGER.info("---------getGroupLists End-----------");
        return userGroupMappingRepository.findAllGroupsByUsername(username);
    }

    @Override
    public Boolean removeGroup(Long group_id) {
        LOGGER.info("---------removeGroup Start-----------");
        userGroupMappingRepository.deleteById(group_id);
        return userGroupMappingRepository.existsByGroupId(group_id);
    }

    @Override
    public String checkGroupRole(Long group_id) {
        LOGGER.info("---------checkGroupRole Start-----------");
        String username = securityUtil.getCurrentUsername();
        return userGroupMappingRepository.findGroupRoleByUsernameAndGroupId(username, group_id);
    }

    @Override
    public GroupEntity groupInfo(Long group_id) {
        LOGGER.info("---------groupInfo Start-----------");
        return groupRepository.findGroupEntityById(group_id).orElse(null);
    }
}
