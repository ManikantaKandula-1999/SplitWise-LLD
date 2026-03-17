package com.example.splitwiselld.services;

import com.example.splitwiselld.models.ExpenseGroup;
import com.example.splitwiselld.models.User;
import com.example.splitwiselld.repositories.ExpenseGroupRepository;
import com.example.splitwiselld.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseGroupServiceImpl implements ExpenseGroupService{

    private final ExpenseGroupRepository expenseGroupRepository;
    private final UserRepository userRepository;

    public ExpenseGroupServiceImpl(ExpenseGroupRepository expenseGroupRepository, UserRepository userRepository) {
        this.expenseGroupRepository = expenseGroupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ExpenseGroup createGroup(String groupName, Long ownerId, List<Long> memberIds){
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        List<User> users = userRepository.findAllById(memberIds);

        users.add(owner);

        ExpenseGroup expenseGroup = ExpenseGroup.builder()
                .groupName(groupName)
                .owner(owner)
                .members(users)
                .build();

        return expenseGroupRepository.save(expenseGroup);
    }

    public ExpenseGroup getGroup(Long groupId){
        return expenseGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }

    public ExpenseGroup addMember(Long groupId, Long ownerId, Long userId){
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));

        if(!expenseGroup.getOwner().getId().equals(ownerId)){
            throw new IllegalArgumentException("Only owner can add members");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        expenseGroup.getMembers().add(user);
        return expenseGroupRepository.save(expenseGroup);
    }

    public ExpenseGroup removeMember(Long groupId, Long ownerId, Long userId){
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));

        if(!expenseGroup.getOwner().getId().equals(ownerId)){
            throw new IllegalArgumentException("Only owner can remove members");
        }

        expenseGroup.setMembers(expenseGroup.getMembers().stream()
                .filter(member -> !member.getId().equals(userId))
                .toList());
        return expenseGroupRepository.save(expenseGroup);
    }
}
