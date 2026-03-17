package com.example.splitwiselld.controllers;

import com.example.splitwiselld.dto.ExpenseRequestDTO;
import com.example.splitwiselld.models.Expense;
import com.example.splitwiselld.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO){
        Expense expense = expenseService.addExpenseWithStrategy(
            expenseRequestDTO.getExpense(),
            expenseRequestDTO.getPaidMap(),
            expenseRequestDTO.getPercentageMap()
        );
        return ResponseEntity.ok(expense);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long userId){
        List<Expense> expenses = expenseService.getUserExpenses(userId);
        return ResponseEntity.ok(expenses);
    }
}
