package com.example.splitwiselld.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expense extends BaseClass{

    @ManyToOne
    @JoinColumn(name = "expense_group")
    private ExpenseGroup expenseGroup;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<Split> splits = new ArrayList<>();

    @Column(name = "split_type")
    @Enumerated(EnumType.STRING)
    private SplitType splitType;

    @Transient
    private List<Long> userIds;
}
