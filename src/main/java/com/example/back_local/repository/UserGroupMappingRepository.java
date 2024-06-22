package com.example.back_local.repository;

import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.sun.jdi.LongValue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @PackageName : com.example.back_local.repository
 * @FileName : UserGroupMappingEntity
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Long> {

    @Query("SELECT ugm.group FROM UserGroupMappingEntity ugm  WHERE ugm.user.username = :username")
    List<GroupEntity> findAllGroupsByUsername(String username);

    @Query("SELECT ugm.group_role FROM UserGroupMappingEntity ugm WHERE ugm.user.username = :username and ugm.group.id = :id")
    String findGroupRoleByUsernameAndGroupId(String username, Long group_id);

    @Query("SELECT CASE WHEN COUNT(ugm) > 0 THEN true ELSE false END FROM UserGroupMappingEntity ugm WHERE ugm.group.id = :id")
    Boolean existsByGroupId(Long group_id);
}
