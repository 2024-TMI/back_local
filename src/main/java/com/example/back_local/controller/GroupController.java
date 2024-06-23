package com.example.back_local.controller;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.dto.group.GroupCreateDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.example.back_local.repository.UserGroupMappingRepository;
import com.example.back_local.service.GroupService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<GroupAfterCreateDto> createGroup(@RequestBody GroupCreateDto groupCreateDto){
        LOGGER.info("---------createGroup Start----------");
        LOGGER.info("groupName : {}, type : {}", groupCreateDto.getGroup_name(), groupCreateDto.getGroup_category());
        String groupName = groupCreateDto.getGroup_name();
        String groupType = groupCreateDto.getGroup_category();

        GroupAfterCreateDto groupAfterCreateDto = groupService.createGroup(groupName, groupType);

        if(groupAfterCreateDto != null){
            LOGGER.info("---------createGroup End----------");
            return ResponseEntity.ok(groupAfterCreateDto);
        }
        LOGGER.info("---------createGroup End null----------");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<GroupEntity>> getGroupLists(){
        LOGGER.info("---------getGroupLists Start----------");
        List<GroupEntity> groupLists= groupService.getGroupLists();
        LOGGER.info("---------getGroupLists End----------");
        return ResponseEntity.ok(groupLists);
    }

    @PostMapping("/remove")
    public ResponseEntity<Map<String, String>> removeGroup(Long group_id){
        LOGGER.info("---------removeGroup Start----------");

        Map<String, String> responseData = new HashMap<>();
        String groupRole = groupService.checkGroupRole(group_id);
        if(groupRole.equals("Member")){
            responseData.put("msg", "Not Manager");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        Boolean tf = groupService.removeGroup(group_id);
        if(tf){
            responseData.put("msg", "Not Clearly removed");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        responseData.put("msg", "Clearly removed");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping("/configuration")
    public ResponseEntity<GroupEntity> getGroupInfo(Long group_id){
        LOGGER.info("---------getGroupInfo Start----------");
        String groupRole = groupService.checkGroupRole(group_id);
        if(groupRole.equals("Member")){
            LOGGER.info("---------getGroupInfo End -> Not Manager----------");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        GroupEntity findGroupEntity = groupService.groupInfo(group_id);
        LOGGER.info("---------getGroupInfo End----------");
        return ResponseEntity.ok(findGroupEntity);
    }
}
