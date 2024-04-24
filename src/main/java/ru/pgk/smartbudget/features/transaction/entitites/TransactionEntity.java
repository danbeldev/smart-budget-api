package ru.pgk.smartbudget.features.transaction.entitites;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

import java.time.LocalDate;

@Data
@Entity(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExpenseCategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurrencyEntity currencyCode;
    private Double amountInCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurrencyEntity baseCurrencyCode;
    private Double amountInBaseCurrency;
}
