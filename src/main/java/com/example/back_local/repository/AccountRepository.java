package com.example.back_local.repository;

import com.example.back_local.entity.AccountEntity;
import com.example.back_local.entity.GroupEntity;
import java.util.List;
import org.springdoc.core.providers.JavadocProvider;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @PackageName : com.example.back_local.repository
 * @FileName : AccountEntity
 * @Author : noglass_gongdae
 * @Date : 2024-06-22
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

//    List<AccountEntity> findAccountEntitiesBy

}
