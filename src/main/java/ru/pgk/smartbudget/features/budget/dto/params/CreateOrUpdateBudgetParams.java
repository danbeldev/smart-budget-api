package ru.pgk.smartbudget.features.budget.dto.params;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateOrUpdateBudgetParams {

    @NotNull(message = "amountLimit must be not null")
    @DecimalMin(value = "0.1", message = "amountLimit must be greater than or equal to 0.1")
    private Double amountLimit;
    private LocalDate startDate = LocalDate.now();
    @NotNull(message = "endDate must be not null")
    private LocalDate endDate;
    @NotNull(message = "categoryId must be not null")
    private Integer categoryId;
}
