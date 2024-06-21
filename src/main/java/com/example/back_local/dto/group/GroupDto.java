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
    @JsonProperty("name")
    private String groupName;

    private String type;
}
