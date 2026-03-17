package com.example.splitwiselld.dto;

import com.example.splitwiselld.models.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDTO {
    private Expense expense;
    private Map<Long, Double> paidMap;
    private Map<Long, Double> percentageMap;
}
