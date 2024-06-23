package com.example.back_local.repository;

import com.example.back_local.entity.GroupEntity;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

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

    void deleteById(@NonNull Long id);
    // Not annotated parameter overrides @NonNullApi parameter
    // => @NonNull 붙이기

}
