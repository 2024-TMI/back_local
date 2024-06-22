package com.example.back_local.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "user_group_mapping")
public class UserGroupMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "username", nullable = false)
//    private String username;
//
//    @Column(name = "group_id", nullable = false)
//    private String groupId;

    private String group_role;

    @ManyToOne
    @JoinColumn(name = "user_username") //, insertable = false, updatable = false
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "group_id")  //, insertable = false, updatable = false
    private GroupEntity group;
}
