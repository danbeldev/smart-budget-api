package ru.pgk.smartbudget.features.transaction.dto.params;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTransactionParams {

    private final Integer categoryId;
    private final Short currencyCodeId = null;
    private final Double amount;
    private final String description = null;
    private final LocalDate date = LocalDate.now();
}
