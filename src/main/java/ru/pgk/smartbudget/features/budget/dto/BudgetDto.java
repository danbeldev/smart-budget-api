package ru.pgk.smartbudget.features.budget.dto;

import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;

import java.time.LocalDate;

public record BudgetDto(
        Long id,
        Double amountLimit,
        Double currentAmount,
        LocalDate startDate,
        LocalDate endDate,
        ExpenseCategoryDto category
) {}
