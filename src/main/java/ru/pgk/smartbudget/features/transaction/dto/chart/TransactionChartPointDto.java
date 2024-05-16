package ru.pgk.smartbudget.features.transaction.dto.chart;

import ru.pgk.smartbudget.features.expenseCategory.dto.ExpenseCategoryDto;

public record TransactionChartPointDto(
        Double value,
        ExpenseCategoryDto category
) {}
