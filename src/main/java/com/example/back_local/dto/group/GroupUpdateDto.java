package com.example.back_local.dto.group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupUpdateDto {

    private Long id;
    private String group_name;
    private String section;
    private String group_category;

}
