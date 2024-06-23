package com.example.back_local.repository;

import com.example.back_local.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @PackageName : com.example.back_local.repository
 * @FileName : GroupRepository
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findGroupEntityById(Long group_id);

}
