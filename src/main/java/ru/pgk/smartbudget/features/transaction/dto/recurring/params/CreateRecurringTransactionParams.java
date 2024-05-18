package ru.pgk.smartbudget.features.transaction.dto.recurring.params;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;

import java.time.LocalDate;

public record CreateRecurringTransactionParams(
        @NotNull(message = "name must be not null")
        @Length(max = 48, message = "name must be smaller than 48 symbols")
        String name,
        @NotNull(message = "amount must be not null")
        @DecimalMin(value = "0.1", message = "amount must be greater than or equal to 0.1")
        Double amount,
        @NotNull(message = "startDate must be not null")
        LocalDate startDate,
        LocalDate endDate,
        @NotNull(message = "frequency must be not null")
        RecurringTransactionFrequencyEntity.Name frequency,
        @NotNull(message = "categoryId must be not null")
        Integer categoryId
) {}
