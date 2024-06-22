package com.example.back_local.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group_entity")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invite_code; //초대 코드
    private String group_name; //그룹명
    private String section; //날짜~날짜
    private String group_category; //그룹 종류
    private Long total; //총 인원
    private String date; //?
    private Long total_amount; //총 비용

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroupMappingEntity> userGroupMappings ;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> accounts;
}
