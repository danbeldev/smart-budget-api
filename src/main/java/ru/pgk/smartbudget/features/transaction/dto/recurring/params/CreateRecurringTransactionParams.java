package ru.pgk.smartbudget.features.transaction.dto.recurring.params;

import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;

import java.time.LocalDate;

public record CreateRecurringTransactionParams(
        String name,
        Float amount,
        LocalDate startDate,
        RecurringTransactionFrequencyEntity.Name frequency,
        Integer categoryId
) {}
