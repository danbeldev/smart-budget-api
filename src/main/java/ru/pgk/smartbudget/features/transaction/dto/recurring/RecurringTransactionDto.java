package ru.pgk.smartbudget.features.transaction.dto.recurring;

import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;
import ru.pgk.smartbudget.features.transaction.entitites.recurring.frequency.RecurringTransactionFrequencyEntity;

import java.time.LocalDate;

public record RecurringTransactionDto(
   Long id,
   String name,
   Float amount,
   RecurringTransactionFrequencyEntity.Name frequency,
   LocalDate startDate,
   Boolean isAchieved,
   ExpenseCategoryDto category
) {}
