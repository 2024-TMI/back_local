package com.example.back_local.service.impl;

import com.example.back_local.dto.account.AccountAddDto;
import com.example.back_local.dto.account.AccountListRequestDto;
import com.example.back_local.dto.account.AccountListResponseDto;
import com.example.back_local.entity.AccountEntity;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.generation.MakeDto;
import com.example.back_local.generation.MakeEntity;
import com.example.back_local.repository.AccountRepository;
import com.example.back_local.repository.GroupRepository;
import com.example.back_local.service.AccountService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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
    private final MakeDto makeDto;

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
    public List<AccountListResponseDto> getAccountList(LocalDate date, Long group_id) {

        LocalDateTime startDay = date.atStartOfDay();
        LocalDateTime endDay = startDay.toLocalDate().atTime(LocalTime.MAX);

        List<AccountEntity> accountEntities = accountRepository.findByDateOrderByTimeAsc(startDay, endDay);
        if(accountEntities == null){
            return null;
        }
        List<AccountListResponseDto> ALRDto = makeDto.makeALRDto(accountEntities);
        return ALRDto;
    }
}
