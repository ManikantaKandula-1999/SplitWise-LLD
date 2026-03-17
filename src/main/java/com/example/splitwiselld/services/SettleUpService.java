package com.example.splitwiselld.services;

import com.example.splitwiselld.dto.BalanceDTO;
import com.example.splitwiselld.models.Split;

import java.util.List;

public interface SettleUpService {

    List<BalanceDTO> settleUpGroup(Long groupId);

    List<BalanceDTO> settleUpUser(Long userId);

    List<BalanceDTO> getTransactionsToSettleUp(List<Split> splits);
}
