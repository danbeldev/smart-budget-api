package ru.pgk.smartbudget.features.transaction.dto;

import ru.pgk.smartbudget.features.currency.dto.CurrencyDto;
import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;

import java.time.LocalDate;

public record TransactionDto(
        Long id,
        String description,
        LocalDate date,
        ExpenseCategoryDto category,
        CurrencyDto currencyCode,
        Double amountInCurrency,
        CurrencyDto baseCurrencyCode,
        Double amountInBaseCurrency
) {}
