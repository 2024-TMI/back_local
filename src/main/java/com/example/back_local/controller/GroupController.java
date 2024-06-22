package com.example.back_local.controller;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.dto.group.GroupDto;
import com.example.back_local.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    private Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @PostMapping("/create")
    public ResponseEntity<GroupAfterCreateDto> createGroup(@RequestBody GroupDto groupDto){
        LOGGER.info("---------createGroup Start----------");
        LOGGER.info("groupName : {}, type : {}", groupDto.getGroup_name(), groupDto.getGroup_category());
        String groupName = groupDto.getGroup_name();
        String groupType = groupDto.getGroup_category();

        GroupAfterCreateDto groupAfterCreateDto = groupService.createGroup(groupName, groupType);

        if(groupAfterCreateDto != null){
            LOGGER.info("---------createGroup End----------");
            return ResponseEntity.ok(groupAfterCreateDto);
        }
        LOGGER.info("---------createGroup End null----------");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

//    @GetMapping("/lists")
//    public void getGroupLists(@RequestBody ){
//
//
//
//    }
}
