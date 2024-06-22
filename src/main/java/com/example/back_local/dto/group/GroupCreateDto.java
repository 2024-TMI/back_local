package com.example.back_local.dto.group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GroupCreateDto {

    private String group_name;

    private String group_category;
}
