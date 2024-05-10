package ru.pgk.smartbudget.features.budget.dto.params;

import lombok.Getter;

@Getter
public enum BudgetOrderByType {
    START_DATE_ASC("startDate", false),
    START_DATE_DESC("startDate", true),
    END_DATE_ASC("endDate", false),
    END_DATE_DESC("endDate", true),
    AMOUNT_LIMIT_ASC("amountLimit", false),
    AMOUNT_LIMIT_DESC("amountLimit", true);

    private final String fieldName;
    private final boolean descending;

    BudgetOrderByType(String fieldName, boolean descending) {
        this.fieldName = fieldName;
        this.descending = descending;
    }
}