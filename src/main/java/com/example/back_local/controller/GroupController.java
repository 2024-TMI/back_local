package com.example.back_local.controller;

import com.example.back_local.dto.group.GroupDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    private Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestBody GroupDto groupDto){
        LOGGER.info("---------createGroup----------");
        LOGGER.info("groupName : {}, type : {}", groupDto.getGroupName(), groupDto.getType());

        return ResponseEntity.ok("Hello");
    }
}
