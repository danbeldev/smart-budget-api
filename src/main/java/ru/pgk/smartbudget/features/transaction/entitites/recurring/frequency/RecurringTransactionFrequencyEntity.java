package ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.RecurringTransactionEntity;

import java.util.Collection;

@Data
@Entity(name = "recurring_transaction_frequencies")
public class RecurringTransactionFrequencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(EnumType.STRING)
    private Name name;

    @OneToMany(mappedBy = "frequency", fetch = FetchType.LAZY)
    private Collection<RecurringTransactionEntity> recurringTransactions;

    public enum Name {
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }
}
