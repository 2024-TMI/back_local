package com.example.back_local.service;

import com.example.back_local.dto.account.AccountAddDto;
import com.example.back_local.dto.account.AccountListRequestDto;
import com.example.back_local.dto.account.AccountListResponseDto;
import com.example.back_local.entity.AccountEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @PackageName : com.example.back_local.service.impl
 * @FileName : AccountService
 * @Author : noglass_gongdae
 * @Date : 2024-06-24
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
public interface AccountService {

    AccountEntity saveAccount(AccountAddDto accountAddDto);

    List<AccountListResponseDto> getAccountList(LocalDate date, Long group_id);

}
