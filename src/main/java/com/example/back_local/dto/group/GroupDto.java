package com.example.back_local.dto.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GroupDto {

    private String group_name;

    private String group_category;
}
