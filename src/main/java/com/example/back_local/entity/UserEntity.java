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
@Table(name = "user_table")
public class UserEntity {
    @Id
    @Column(name = "username")
    private String username;

    private String nickname;
    private String role;
    private String profile_image;
    private String email;
    private String provider;
    private String providerId;
    private String password;
    private String thumbnail_image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroupMappingEntity> userGroupMappings = new ArrayList<>();
}
