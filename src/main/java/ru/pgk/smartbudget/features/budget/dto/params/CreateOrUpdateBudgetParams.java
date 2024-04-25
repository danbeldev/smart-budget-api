package ru.pgk.smartbudget.features.budget.dto.params;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateOrUpdateBudgetParams {

    private Double amountLimit;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = null;
    private Short categoryId;
}
