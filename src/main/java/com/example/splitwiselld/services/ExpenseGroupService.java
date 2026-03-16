package com.example.splitwiselld.services;

import com.example.splitwiselld.models.ExpenseGroup;
import com.example.splitwiselld.models.User;

import java.util.List;

public interface ExpenseGroupService {

    ExpenseGroup createGroup(String groupName, Long ownerId, List<Long> memberIds);

    ExpenseGroup getGroup(Long groupId);

    ExpenseGroup addMember(Long groupId, Long ownerId, Long userId);

    ExpenseGroup removeMember(Long groupId, Long ownerId, Long userId);
}
