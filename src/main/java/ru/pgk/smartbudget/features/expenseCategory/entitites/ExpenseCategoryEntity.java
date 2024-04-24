package ru.pgk.smartbudget.features.expenseCategory.entitites;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.smartbudget.features.budget.entities.BudgetEntity;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

import java.util.Collection;

@Data
@Entity(name = "expense_categories")
public class ExpenseCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Collection<TransactionEntity> transactions;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Collection<BudgetEntity> budgets;
}
