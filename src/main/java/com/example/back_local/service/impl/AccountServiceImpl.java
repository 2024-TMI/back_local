package com.example.back_local.service.impl;

import com.example.back_local.dto.account.AccountAddDto;
import com.example.back_local.dto.account.AccountListDto;
import com.example.back_local.entity.AccountEntity;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.generation.MakeEntity;
import com.example.back_local.repository.AccountRepository;
import com.example.back_local.repository.GroupRepository;
import com.example.back_local.service.AccountService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @PackageName : com.example.back_local.service.impl
 * @FileName : AccountServiceImpl
 * @Author : noglass_gongdae
 * @Date : 2024-06-24
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final GroupRepository groupRepository;
    private final AccountRepository accountRepository;
    private final MakeEntity makeEntity;

    @Override
    public AccountEntity saveAccount(AccountAddDto accountAddDto) {

        GroupEntity groupEntity = groupRepository.findGroupEntityById(accountAddDto.getGroup_id()).orElse(null);
        GroupEntity savedGroup;
        if(groupEntity == null)
            return null;
        if(accountAddDto.getClassification().equals("지출")){
            groupEntity.setTotal_amount(groupEntity.getTotal_amount() - accountAddDto.getAmount());
        }
        else {
            groupEntity.setTotal_amount(groupEntity.getTotal_amount() + accountAddDto.getAmount());
        }
        savedGroup = groupRepository.save(groupEntity);
        AccountEntity createAccount = makeEntity.makeAccountEntity(accountAddDto, savedGroup);
        AccountEntity savedAccount = accountRepository.save(createAccount);

        return savedAccount;
    }

    @Override
    public void getAccountList(AccountListDto accountListDto) {



    }
}
