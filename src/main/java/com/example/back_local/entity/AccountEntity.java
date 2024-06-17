package com.example.back_local.entity;

import jakarta.persistence.*;
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
    @Column(name = "account_id")
    private Long accountId;

    private String date;
    private String classification;
    private String category;
    private String businessName;
    private String amount;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;
}
