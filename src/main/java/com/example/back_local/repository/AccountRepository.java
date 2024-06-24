package com.example.back_local.repository;

import com.example.back_local.entity.AccountEntity;
import com.example.back_local.entity.GroupEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springdoc.core.providers.JavadocProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @PackageName : com.example.back_local.repository
 * @FileName : AccountEntity
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query("SELECT a FROM AccountEntity a WHERE a.date BETWEEN :startOfDay AND :endOfDay ORDER BY a.date ASC")
    List<AccountEntity> findByDateOrderByTimeAsc(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
