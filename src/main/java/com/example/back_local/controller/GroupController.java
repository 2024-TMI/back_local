package com.example.back_local.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {

    private Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestParam("name") String groupName, @RequestParam("type") String type){
        LOGGER.info("---------createGroup----------");
        LOGGER.info("groupName : {}, type : {}", groupName, type);

        return ResponseEntity.ok("Hello");
    }
}
