package com.example.back_local.repository;

import com.example.back_local.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @PackageName : com.example.back_local.repository
 * @FileName : UserRepository
 * @Author : noglass_gongdae
 * @Date : 2024-05-23
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
