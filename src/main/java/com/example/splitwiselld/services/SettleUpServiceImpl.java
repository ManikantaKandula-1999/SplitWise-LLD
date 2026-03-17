package com.example.splitwiselld.services;

import com.example.splitwiselld.dto.BalanceDTO;
import com.example.splitwiselld.models.Split;
import com.example.splitwiselld.repositories.SplitRepository;
import com.example.splitwiselld.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpServiceImpl implements SettleUpService {

    private final SplitRepository splitRepository;
    private final UserRepository userRepository;

    public SettleUpServiceImpl(SplitRepository splitRepository, UserRepository userRepository) {
        this.splitRepository = splitRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BalanceDTO> settleUpGroup(Long groupId) {
        List<Split> splits = splitRepository.findAll().stream()
                .filter(split -> split.getExpense().getExpenseGroup() != null &&
                        split.getExpense().getExpenseGroup().getId().equals(groupId))
                .toList();
        return getTransactionsToSettleUp(splits);
    }

    public List<BalanceDTO> settleUpUser(Long userId) {
        List<Split> splits = splitRepository.findAll().stream()
                .filter(split -> split.getUser().getId().equals(userId) ||
                        split.getExpense().getExpenseGroup().getOwner().getId().equals(userId))
                .toList();
        return getTransactionsToSettleUp(splits);
    }

    public List<BalanceDTO> getTransactionsToSettleUp(List<Split> splits) {
        Map<Long, Double> netMap = new HashMap<>();

        for(Split split : splits){
            Long userId = split.getUser().getId();
            Double net = split.getPaid() - split.getOwed();
            netMap.put(userId, netMap.getOrDefault(userId, 0.0) + net);
        }

        PriorityQueue<Map.Entry<Long, Double>> debtors = new PriorityQueue<>(Map.Entry.comparingByValue());
        PriorityQueue<Map.Entry<Long, Double>> creditors = new PriorityQueue<>((a,b) -> Double.compare(b.getValue(), a.getValue()));

        for(Map.Entry<Long, Double> entry : netMap.entrySet()){
            if(entry.getValue() < 0){
                debtors.add(entry);
            }else if(entry.getValue() > 0){
                creditors.add(entry);
            }
        }

        List<BalanceDTO> transactions = new ArrayList<>();

        while(!debtors.isEmpty() && !creditors.isEmpty()){
            var debtor = debtors.poll();
            var creditor = creditors.poll();

            double settled = Math.min(-debtor.getValue(), creditor.getValue());

            transactions.add(new BalanceDTO(
                    userRepository.findById(debtor.getKey()).orElseThrow(),
                    userRepository.findById(creditor.getKey()).orElseThrow(),
                    settled
            ));

            double remainingDebt = debtor.getValue() + settled;
            double remainingCreditor = creditor.getValue() - settled;

            if(remainingDebt < 0){
                debtors.add(Map.entry(debtor.getKey(), remainingDebt));
            }

            if(remainingCreditor > 0){
                creditors.add(Map.entry(creditor.getKey(), remainingCreditor));
            }

        }
        return transactions;
    }
}
