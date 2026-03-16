package com.example.splitwiselld.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ExpenseGroup extends BaseClass{

    private String groupName;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "expense_group_members",
            joinColumns = @JoinColumn(name = "expense_group_id"),
            inverseJoinColumns = @JoinColumn(name="member_id")
    )
    private List<User> members;
}
