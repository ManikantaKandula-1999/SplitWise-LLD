package com.example.splitwiselld.dto;

import com.example.splitwiselld.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {
    private User from;
    private User to;
    private Double balance;
}
