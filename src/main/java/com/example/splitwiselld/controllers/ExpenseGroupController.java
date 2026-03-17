package com.example.splitwiselld.controllers;

import com.example.splitwiselld.dto.GroupRequestDTO;
import com.example.splitwiselld.models.ExpenseGroup;
import com.example.splitwiselld.services.ExpenseGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class ExpenseGroupController {

    private final ExpenseGroupService expenseGroupService;

    @PostMapping("/create")
    public ResponseEntity<ExpenseGroup> createGroup(@RequestBody GroupRequestDTO groupRequestDTO){
        ExpenseGroup expenseGroup = expenseGroupService.createGroup(
                groupRequestDTO.getGroupName(),
                groupRequestDTO.getOwnerId(),
                groupRequestDTO.getMemberIds()
        );
        return ResponseEntity.ok(expenseGroup);
    }

    @PostMapping("/{groupId}/add")
    public ResponseEntity<ExpenseGroup> addMember(@PathVariable Long groupId, @RequestParam Long ownerId ,@RequestParam Long userId){
        ExpenseGroup expenseGroup = expenseGroupService.addMember(groupId, ownerId, userId);
        return ResponseEntity.ok(expenseGroup);
    }

    @DeleteMapping("/{groupId}/remove")
    public ResponseEntity<ExpenseGroup> removeMember(@PathVariable Long groupId, @RequestParam Long ownerId ,@RequestParam Long userId){
        ExpenseGroup expenseGroup = expenseGroupService.removeMember(groupId, ownerId, userId);
        return ResponseEntity.ok(expenseGroup);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ExpenseGroup> getGroup(@PathVariable Long groupId){
        ExpenseGroup expenseGroup = expenseGroupService.getGroup(groupId);
        return ResponseEntity.ok(expenseGroup);
    }
}
