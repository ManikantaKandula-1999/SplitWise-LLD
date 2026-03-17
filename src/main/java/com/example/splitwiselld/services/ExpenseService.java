package com.example.splitwiselld.services;

import com.example.splitwiselld.models.Expense;

import java.util.List;
import java.util.Map;

public interface ExpenseService {

    Expense addExpenseWithStrategy(Expense expense, Map<Long, Double> paidMap, Map<Long, Double> percentageMap);

    List<Expense> getUserExpenses(Long userId);
}
