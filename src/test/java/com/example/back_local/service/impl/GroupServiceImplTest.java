package com.example.back_local.service.impl;

import com.example.back_local.dto.group.GroupAfterCreateDto;
import com.example.back_local.entity.GroupEntity;
import com.example.back_local.entity.UserEntity;
import com.example.back_local.entity.UserGroupMappingEntity;
import com.example.back_local.generation.GenerationThings;
import com.example.back_local.generation.MakeDto;
import com.example.back_local.generation.MakeEntity;
import com.example.back_local.repository.GroupRepository;
import com.example.back_local.repository.UserGroupMappingRepository;
import com.example.back_local.repository.UserRepository;
import com.example.back_local.security.SecurityUtil;
import com.example.back_local.service.GroupService;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.type.ArrayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private MakeDto makeDto;
    @Mock
    private MakeEntity makeEntity;

    @Mock
    private GenerationThings generationThings;
    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserGroupMappingRepository userGroupMappingRepository;

    @InjectMocks
    private GroupServiceImpl groupServiceImpl;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        if (testInfo.getDisplayName().equals("removeGroup_ReturnTrue_WhenCorrectRequest")) {
            return;
        }
        if (testInfo.getDisplayName().equals("groupInfo_returnGroupEntity_WhenCorrectRequest")) {
            return;
        }
        // Configure the mock behavior
        when(securityUtil.getCurrentUsername()).thenReturn("exampleUser");
    }

    @Test
    void createGroup_ReturnsDto_WhenValidRequest() {
        // Given
        String groupName = "TestGroup";
        String groupCategory = "TestCategory";
        String inviteCode = "INV123";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("exampleUser");

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroup_name(groupName);
        groupEntity.setGroup_category(groupCategory);
        groupEntity.setInvite_code(inviteCode);

        UserGroupMappingEntity mappingEntity = new UserGroupMappingEntity();
        mappingEntity.setGroup_role("Manager");
        mappingEntity.setGroup(groupEntity);
        mappingEntity.setUser(userEntity);

        GroupAfterCreateDto groupAfterCreateDto = new GroupAfterCreateDto();
        groupAfterCreateDto.setGroup_name(groupName);
        groupAfterCreateDto.setGroup_category(groupCategory);
        groupAfterCreateDto.setGroup_role(mappingEntity.getGroup_role());
        groupAfterCreateDto.setInvite_code(inviteCode);
        groupAfterCreateDto.setUsername(userEntity.getUsername());

        when(generationThings.generateInviteCode(anyString())).thenReturn(inviteCode);
        when(makeEntity.makeGroupEntity(anyString(), anyString(), anyString())).thenReturn(groupEntity);
        when(groupRepository.save(any(GroupEntity.class))).thenReturn(groupEntity);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));
        when(makeEntity.makeUserGroupMappingEntity(any(UserEntity.class), any(GroupEntity.class))).thenReturn(mappingEntity);
        when(userGroupMappingRepository.save(any(UserGroupMappingEntity.class))).thenReturn(mappingEntity);
        when(makeDto.makeGroupAfterCreateDto(any(UserEntity.class), any(GroupEntity.class), any(UserGroupMappingEntity.class))).thenReturn(groupAfterCreateDto);
        // When
        GroupAfterCreateDto result = groupServiceImpl.createGroup(groupName, groupCategory);

        // Then
        assertNotNull(result);
        assertEquals(groupName, result.getGroup_name());
        assertEquals(groupCategory, result.getGroup_category());
        assertEquals("exampleUser", result.getUsername());
    }

    @Test
    void createGroup_ReturnsNull_WhenUsernameIsNull() {
        // Given
        when(securityUtil.getCurrentUsername()).thenReturn(null);

        // When
        GroupAfterCreateDto result = groupServiceImpl.createGroup("TestGroup", "TestCategory");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("removeGroup_ReturnTrue_WhenCorrectRequest")
    void removeGroup_ReturnTrue_WhenCorrectRequest(){

        Long group_id = 2L;
        Boolean tf = true;

        doNothing().when(groupRepository).deleteById(group_id);
        when(userGroupMappingRepository.existsByGroupId(group_id)).thenReturn(false);

        Boolean result = groupServiceImpl.removeGroup(group_id);
        assertFalse(result, "must not exist group");

        verify(groupRepository, times(1)).deleteById(group_id);
        verify(userGroupMappingRepository, times(1)).existsByGroupId(group_id);
    }

    @Test
    void checkGroupRole_returnGroupRole_WhenCorrectRequest(){

        Long group_id = 2L;
        String group_role = "Manager";

        when(userGroupMappingRepository.findGroupRoleByUsernameAndGroupId(anyString(), anyLong())).thenReturn(group_role);

        String result = groupServiceImpl.checkGroupRole(group_id);

        assertNotNull(result);
        assertEquals(result, group_role);
    }

    @Test
    @DisplayName("groupInfo_returnGroupEntity_WhenCorrectRequest")
    void groupInfo_returnGroupEntity_WhenCorrectRequest(){

        Long group_id = 2L;
        GroupEntity groupEntity = GroupEntity.builder()
            .group_name("Group1")
            .group_category("운동")
            .invite_code("INV123")
            .build();

        when(groupRepository.findGroupEntityById(group_id)).thenReturn(Optional.of(groupEntity));

        GroupEntity result = groupServiceImpl.groupInfo(group_id);
        assertNotNull(result);
        assertEquals(result.getGroup_name(), groupEntity.getGroup_name());

        // 해당 메서드가 정확히 1번 호출되었는지 확인함
        // group_id를 받은 findGroupEntityById가
        // groupRepository 객체에서 정확히 한번 호출되는지 검증
        verify(groupRepository, times(1)).findGroupEntityById(group_id);
    }
}