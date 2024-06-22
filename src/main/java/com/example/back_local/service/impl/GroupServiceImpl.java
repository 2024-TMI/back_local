package com.example.back_local.service.impl;

import com.example.back_local.dto.CustomUserDetails;
import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.example.back_local.repository.GroupRepository;
import com.example.back_local.repository.UserGroupMappingRepository;
import com.example.back_local.repository.UserRepository;
import com.example.back_local.security.SecurityUtil;
import com.example.back_local.service.GroupService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.swing.GroupLayout.Group;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
@Transactional
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
        GroupEntity saveGroup = saveGroupEntity(groupEntity);

        String username = SecurityUtil.getCurrentUsername();
        if(username == null){
            return null;
        }
        UserEntity userEntity = getUserEntity(username);
        if(userEntity == null){
            return null;
        }
        UserGroupMappingEntity userGroupMappingEntity = makeUserGroupMappingEntity(userEntity, groupEntity);

        // add 할 필요없음 그냥 저장하면 됨 순서대로
//        userEntity.getUserGroupMappings().add(userGroupMappingEntity);
//        UserEntity saveUser = saveUserEntity(userEntity);
//
//        saveGroup.getUserGroupMappings().add(userGroupMappingEntity);
//        GroupEntity reSaveGroup = saveGroupEntity(saveGroup);

        UserGroupMappingEntity saveUserGroup = saveGroupAndUserGroupMappingEntity(userGroupMappingEntity);
        LOGGER.info("---------createGroup End-----------");
        return makeGroupAfterCreateDto(userEntity, saveGroup, saveUserGroup);
    }
    @Override
    public List<GroupEntity> getGroupLists() {
        String username = SecurityUtil.getCurrentUsername();
        return userGroupMappingRepository.findAllGroupsByUsername(username);
    }
    @Override
    public Boolean removeGroup(Long group_id) {
        deleteGroup(group_id);
        return userGroupMappingRepository.existsByGroupId(group_id);
    }
    @Override
    public String checkGroupRole(Long group_id) {
        String username = SecurityUtil.getCurrentUsername();
        return userGroupMappingRepository.findGroupRoleByUsernameAndGroupId(username, group_id);
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
    public UserEntity getUserEntity(String username){
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
    public GroupEntity saveGroupEntity(GroupEntity groupEntity) {
        return groupRepository.save(groupEntity);
    }
    public UserGroupMappingEntity saveGroupAndUserGroupMappingEntity(UserGroupMappingEntity userGroupMappingEntity){
        return userGroupMappingRepository.save(userGroupMappingEntity);
    }
    public void deleteGroup(Long group_id){
        userGroupMappingRepository.deleteById(group_id);
    }
    public GroupAfterCreateDto makeGroupAfterCreateDto(
        UserEntity userEntity, GroupEntity groupEntity, UserGroupMappingEntity userGroupMappingEntity){
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
