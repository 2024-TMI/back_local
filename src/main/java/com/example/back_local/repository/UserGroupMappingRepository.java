package com.example.back_local.repository;

import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.sun.jdi.LongValue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    String findGroupRoleByUsernameAndGroupId(@Param("username")String username, @Param("id")Long group_id); //웬만하면 param으로 붙여 주기

    @Query("SELECT CASE WHEN COUNT(ugm) > 0 THEN true ELSE false END FROM UserGroupMappingEntity ugm WHERE ugm.group.id = :id")
    Boolean existsByGroupId(@Param("id")Long group_id); //매개변수가 이름과 다른 경우 and 2개 이상인 경우 @Param으로 일치 시켜 주기

    @Query("SELECT ugm.user FROM UserGroupMappingEntity ugm WHERE ugm.group.id = :id")
    List<UserEntity> findAllUsersInGroupByGroupId(@Param("id") Long group_id);
}
