package com.example.back_local.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "account_entity")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String classification;
    private String category;
    private String business_name;
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;
}
