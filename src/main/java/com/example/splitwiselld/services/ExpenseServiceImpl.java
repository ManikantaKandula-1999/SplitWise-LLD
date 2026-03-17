package com.example.splitwiselld.services;

import com.example.splitwiselld.models.Expense;
import com.example.splitwiselld.models.ExpenseGroup;
import com.example.splitwiselld.models.Split;
import com.example.splitwiselld.models.User;
import com.example.splitwiselld.repositories.ExpenseRepository;
import com.example.splitwiselld.repositories.SplitRepository;
import com.example.splitwiselld.repositories.UserRepository;
import com.example.splitwiselld.services.strategy.SplitStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    private final ExpenseRepository expenseRepository;
    private final SplitStrategy splitStrategy;
    private final UserRepository userRepository;
    private final ExpenseGroupService expenseGroupService;
    private final Map<String, SplitStrategy> splitStrategyMap;
    private final SplitRepository splitRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, SplitStrategy splitStrategy, UserRepository userRepository, ExpenseGroupService expenseGroupService, Map<String, SplitStrategy> splitStrategyMap, SplitRepository splitRepository) {
        this.expenseRepository = expenseRepository;
        this.splitStrategy = splitStrategy;
        this.userRepository = userRepository;
        this.expenseGroupService = expenseGroupService;
        this.splitStrategyMap = splitStrategyMap;
        this.splitRepository = splitRepository;
    }

    public Expense addExpenseWithStrategy(Expense expense, Map<Long, Double> paidMap, Map<Long, Double> percentageMap){
        List<User> users;

        if(expense.getExpenseGroup() != null && expense.getExpenseGroup().getId() != null){
            ExpenseGroup group = expenseGroupService.getGroup(expense.getExpenseGroup().getId());
            users = group.getMembers();
            expense.setExpenseGroup(group);
        }else if (expense.getUserIds() != null && !expense.getUserIds().isEmpty()){
            users = userRepository.findAllById(expense.getUserIds());
        }else{
            throw new IllegalArgumentException("Either userIds or expenseGroup must be provided");
        }

        expenseRepository.save(expense);

        SplitStrategy strategy = splitStrategyMap.get(expense.getSplitType().name());
        if(strategy == null){
            throw new IllegalArgumentException("Invalid split type: " + expense.getSplitType());
        }

        List<Split> splits = strategy.calculateSplit(expense, users, paidMap, percentageMap);
        splitRepository.saveAll(splits);
        expense.setSplits(splits);
        return expense;
    };

    public List<Expense> getUserExpenses(Long userId){
       return expenseRepository.findAll().stream()
                .filter(expense -> expense.getSplits().stream().anyMatch(split -> split.getUser().getId().equals(userId)))
                .toList();
    };
}
