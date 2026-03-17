package com.example.splitwiselld.services.strategy;

import com.example.splitwiselld.models.Expense;
import com.example.splitwiselld.models.Split;
import com.example.splitwiselld.models.User;

import java.util.List;
import java.util.Map;

public class PercentageSplitStrategy implements SplitStrategy{

    @Override
    public List<Split> calculateSplit(
            Expense expense,
            List<User> users,
            Map<Long, Double> paidMap,
            Map<Long, Double>percentageMap
    ){
        if(users == null || users.isEmpty()){
            throw new IllegalArgumentException("Users list cannot be empty");
        }

        if(percentageMap == null){
            throw new IllegalArgumentException("Percentage map cannot be null");
        }

        return users.stream()
                .map(user ->{
                    double percentage = percentageMap.getOrDefault(user.getId(), 0.0);
                    double owed = (percentage / 100) * expense.getTotalAmount();
                    return new Split(expense, user, paidMap.getOrDefault(user.getId(), 0.0), owed);
                })
                .toList();
    }
}
