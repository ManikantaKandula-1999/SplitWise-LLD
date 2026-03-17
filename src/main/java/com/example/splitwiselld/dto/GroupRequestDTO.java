package com.example.splitwiselld.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDTO {
    private String groupName;
    private Long ownerId;
    private List<Long> memberIds;
}
