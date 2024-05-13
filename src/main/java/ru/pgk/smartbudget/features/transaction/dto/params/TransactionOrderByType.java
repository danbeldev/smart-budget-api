package ru.pgk.smartbudget.features.transaction.dto.params;

import lombok.Getter;

@Getter
public enum TransactionOrderByType {
    DATE_ASC("date", false),
    DATE_DESC("date", true),
    AMOUNT_IN_BASE_CURRENCY_ASC("amountInBaseCurrency", false),
    AMOUNT_IN_BASE_CURRENCY_DESC("amountInBaseCurrency", false),
    AMOUNT_IN_CURRENCY_ASC("amountInCurrency", false),
    AMOUNT_IN_CURRENCY_DESC("amountInCurrency", false);

    private final String fieldName;
    private final boolean descending;

    TransactionOrderByType(String fieldName, Boolean descending) {
        this.fieldName = fieldName;
        this.descending = descending;
    }
}
