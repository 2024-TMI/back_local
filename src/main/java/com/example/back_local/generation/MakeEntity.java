package com.example.back_local.generation;

import com.example.back_local.dto.account.AccountAddDto;
import com.example.back_local.entity.AccountEntity;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MakeEntity {

    private final Logger LOGGER = LoggerFactory.getLogger(MakeEntity.class);

    public GroupEntity makeGroupEntity(String invite_code, String group_name, String group_category) {
        LOGGER.info("-------makeGroupEntity Start--------");
        Long total = 1L;
        String date = null;
        Long total_amount = 0L;
        LOGGER.info("-------makeGroupEntity End--------");
        return GroupEntity.builder()
                .invite_code(invite_code)
                .group_name(group_name)
                .group_category(group_category)
                .total(total)
                .date(date)
                .total_amount(total_amount)
                .build();
    }


    public UserGroupMappingEntity makeUserGroupMappingEntity(UserEntity userEntity, GroupEntity groupEntity) {
        LOGGER.info("-------makeUserGroupMappingEntity Start--------");
        String group_role = "Manager";
        LOGGER.info("-------makeUserGroupMappingEntity End--------");
        return UserGroupMappingEntity.builder()
                .group_role(group_role)
                .user(userEntity)
                .group(groupEntity)
                .build();
    }

    public AccountEntity makeAccountEntity(AccountAddDto accountAddDto, GroupEntity groupEntity){
        return AccountEntity.builder()
            .amount(accountAddDto.getAmount())
            .business_name(accountAddDto.getBusiness_name())
            .classification(accountAddDto.getClassification())
            .category(accountAddDto.getCategory())
            .date(accountAddDto.getDateTime())
            .group(groupEntity)
            .build();
    }
}
