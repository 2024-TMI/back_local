package com.example.back_local.service;

import com.example.back_local.dto.account.AccountAddDto;
import com.example.back_local.dto.account.AccountListDto;
import com.example.back_local.entity.AccountEntity;

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

    void getAccountList(AccountListDto accountListDto);

}
