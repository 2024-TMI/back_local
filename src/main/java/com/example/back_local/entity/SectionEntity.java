package com.example.back_local.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @PackageName : com.example.back_local.entity
 * @FileName : SectionEntity
 * @Author : noglass_gongdae
 * @Date : 2024-06-24
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "section")
public class SectionEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id") // 컬럼명 : id
    @MapsId // 부모 엔티티의 기본키를 자식 엔티티의 기본키로 매핑
    private GroupEntity group;

    private String section1;
    private String section2;


}
