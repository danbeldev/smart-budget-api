package ru.pgk.smartbudget.features.budget.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

import java.time.LocalDate;

@Data
@Entity(name = "budgets")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amountLimit;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExpenseCategoryEntity category;

    public Double getCurrentAmount() {
        Double amount = 0.0;
        for(TransactionEntity transaction : user.getTransactions()) {
            if(transaction.getCategory().getId().equals(category.getId()) && transaction.getDate().isAfter(startDate)
                            && transaction.getDate().isBefore(endDate)
            ) {
                amount += transaction.getAmountInBaseCurrency();
            }
        }
        return amount;
    }
}
