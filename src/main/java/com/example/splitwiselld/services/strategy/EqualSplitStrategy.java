package com.example.splitwiselld.services.strategy;

import com.example.splitwiselld.models.Expense;
import com.example.splitwiselld.models.Split;
import com.example.splitwiselld.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("EQUAL")
public class EqualSplitStrategy implements SplitStrategy{

    @Override
    public List<Split> calculateSplit(
            Expense expense,
            List<User> users,
            Map<Long, Double> paidMap,
            Map<Long, Double>percentageMap
    ) {
        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException("Users list cannot be empty");
        }

        if (paidMap == null)
            throw new IllegalArgumentException("Paid map cannot be null");

        double totalAmount = expense.getTotalAmount();
        double amountPerUser = totalAmount / users.size();

        List<Split> splits = new ArrayList<>();

        for(User user : users){
            double paid  = paidMap.getOrDefault(user.getId(), 0.0);
            splits.add(new Split(expense, user, paid, amountPerUser));
        }
        return splits;
    }
}
