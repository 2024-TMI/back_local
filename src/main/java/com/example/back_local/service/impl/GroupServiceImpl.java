package com.example.back_local.service.impl;

import com.example.back_local.dto.CustomUserDetails;
import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.example.back_local.repository.GroupRepository;
import com.example.back_local.repository.UserGroupMappingRepository;
import com.example.back_local.repository.UserRepository;
import com.example.back_local.service.GroupService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class GroupServiceImpl implements GroupService {

    private final UserRepository userRepository;
    private final UserGroupMappingRepository userGroupMappingRepository;
    private final GroupRepository groupRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Override
    public GroupAfterCreateDto createGroup(String group_name, String group_category) {
        LOGGER.info("---------createGroup Start-----------");

        String invite_code = generateInviteCode(group_name);
        GroupEntity groupEntity = makeGroupEntity(invite_code, group_name, group_category);
        String username = getCurrentUsername();
        if(username == null){
            return null;
        }
        UserEntity userEntity = getUserEntity(username);
        if(userEntity == null){
            return null;
        }
        UserGroupMappingEntity userGroupMappingEntity = makeUserGroupMappingEntity(userEntity, groupEntity);

        return saveGroupAndUserGroupMappingEntity(userEntity, groupEntity,userGroupMappingEntity);
    }

    private String generateInviteCode(String group_name){
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid + " -> ");
        byte[] uuidBytes = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;

        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashBytes = messageDigest.digest(uuidBytes);
        } catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < 4; j++){
            sb.append(String.format("%02x",hashBytes[j]));
        }
        System.out.println(sb.toString());
        return group_name + "_" + sb.toString();
    }
    private GroupEntity makeGroupEntity(String invite_code, String group_name, String group_category){
        LOGGER.info("-------makeGroupEntity Start--------");
        String section = null;
        Long total = 1L;
        String date = null;
        Long total_amount = 0L;
        LOGGER.info("-------makeGroupEntity End--------");
        return GroupEntity.builder()
            .invite_code(invite_code)
            .group_name(group_name)
            .section(section)
            .group_category(group_category)
            .total(total)
            .date(date)
            .total_amount(total_amount)
            .build();
    }
    private String getCurrentUsername(){
        LOGGER.info("-------getCurrentUsername Start--------");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            LOGGER.info("-------getCurrentUsername End--------");
            return customUserDetails.getUsername();
        }
        LOGGER.info("-------getCurrentUsername null--------");
        return null;
    }
    private UserEntity getUserEntity(String username){
        return userRepository.findByUsername(username).orElse(null);
    }
    private UserGroupMappingEntity makeUserGroupMappingEntity(UserEntity userEntity, GroupEntity groupEntity) {
        LOGGER.info("-------makeUserGroupMappingEntity Start--------");
        String group_role = "Manager";
        LOGGER.info("-------makeUserGroupMappingEntity End--------");
        return UserGroupMappingEntity.builder()
            .group_role(group_role)
            .user(userEntity)
            .group(groupEntity)
            .build();
    }
    @Transactional //동일한 클래스 내에서 부르면 통하지 않음
    public GroupAfterCreateDto saveGroupAndUserGroupMappingEntity(
        UserEntity userEntity, GroupEntity groupEntity, UserGroupMappingEntity userGroupMappingEntity){
        userEntity.getUserGroupMappings().add(userGroupMappingEntity);

        UserEntity saveUser = userRepository.save(userEntity);
        GroupEntity saveGroup = groupRepository.save(groupEntity);
        UserGroupMappingEntity saveUserGroup = userGroupMappingRepository.save(userGroupMappingEntity);
        return GroupAfterCreateDto.builder()
            .username(saveUser.getUsername())
            .group_id(saveGroup.getId())
            .invite_code(saveGroup.getInvite_code())
            .group_name(saveGroup.getGroup_name())
            .group_category(saveGroup.getGroup_category())
            .total(saveGroup.getTotal())
            .user_group_mapping_id(saveUserGroup.getId())
            .group_role(saveUserGroup.getGroup_role())
            .build();
    }
}
