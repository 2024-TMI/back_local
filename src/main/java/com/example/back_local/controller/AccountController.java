package com.example.back_local.controller;

import com.example.back_local.dto.account.AccountAddDto;
import com.example.back_local.dto.account.AccountListRequestDto;
import com.example.back_local.dto.account.AccountListResponseDto;
import com.example.back_local.entity.AccountEntity;
import com.example.back_local.service.AccountService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName : com.example.back_local.controller
 * @FileName : AccountController
 * @Author : noglass_gongdae
 * @Date : 2024-06-24
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/expenditure")
    public ResponseEntity<Map<String, String>> addAccount(@RequestBody AccountAddDto accountAddDto){
        LOGGER.info("----------addAccount start-----------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        try{
            LocalDateTime date = LocalDateTime.parse(accountAddDto.getDate(), formatter);
            accountAddDto.setDateTime(date);
            Map<String, String> map = new HashMap<>();

            AccountEntity accountEntity = accountService.saveAccount(accountAddDto);
            if(accountEntity == null) {
                map.put("msg", "지출 내역 저장을 실패하였습니다.");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            map.put("msg", "성공적으로 지출 내역을 저장하였습니다.");
            LOGGER.info("----------addAccount End-----------");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/day-list")
    public ResponseEntity<List<AccountListResponseDto>> getDayList(@RequestBody AccountListRequestDto accountListRequestDto){
        LOGGER.info("----------getDayList start-----------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        Long group_id = accountListRequestDto.getGroup_id();
        try {
            LocalDate date = LocalDate.parse(accountListRequestDto.getDate(), formatter);
            List<AccountListResponseDto> ALRDto = accountService.getAccountList(date, group_id);
            if(ALRDto == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            LOGGER.info("----------getDayList end-----------");
            return ResponseEntity.ok(ALRDto);
        } catch (DateTimeParseException e) {
            LOGGER.info("----------getDayList end error-----------");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
