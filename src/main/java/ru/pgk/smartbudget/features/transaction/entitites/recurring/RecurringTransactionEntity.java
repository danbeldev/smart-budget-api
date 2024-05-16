package ru.pgk.smartbudget.features.transaction.entitites.recurring;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

import java.time.LocalDate;

@Data
@Entity(name = "recurring_transactions")
public class RecurringTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Float amount;
    private LocalDate startDate;
    private Boolean isAchieved;

    @ManyToOne(fetch = FetchType.LAZY)
    private RecurringTransactionFrequencyEntity frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExpenseCategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
