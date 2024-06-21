package com.example.back_local.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "group_entity")
public class GroupEntity {
    @Id
    @Column(name = "group_id")
    private String groupId;

    private String groupName;
    private String section;
    private String groupCategory;
    private String username;
    private String total;
    private String date;
    private String totalAmount;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroupMappingEntity> userGroupMappings = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> accounts = new ArrayList<>();

}
