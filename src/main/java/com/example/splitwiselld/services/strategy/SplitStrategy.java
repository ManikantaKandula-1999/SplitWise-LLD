package com.example.splitwiselld.services.strategy;

import com.example.splitwiselld.models.Expense;
import com.example.splitwiselld.models.Split;
import com.example.splitwiselld.models.User;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {

    List<Split> calculateSplit(
            Expense expense,
            List<User> users,
            Map<Long, Double>paidMap,
            Map<Long, Double>percentageMap
    );
}
