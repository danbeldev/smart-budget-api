package ru.pgk.smartbudget.features.transaction.dto.params;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTransactionParams {

    @NotNull(message = "categoryId must be not null")
    private final Integer categoryId;

    @NotNull(message = "targetAmount must be not null")
    @DecimalMin(value = "0.1", message = "amount must be greater than or equal to 0.1")
    private final Double amount;

    private final Short currencyCodeId = null;
    private final String description = null;
    private final LocalDate date = LocalDate.now();
}
