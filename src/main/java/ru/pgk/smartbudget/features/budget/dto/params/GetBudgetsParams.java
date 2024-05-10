package ru.pgk.smartbudget.features.budget.dto.params;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetBudgetsParams {

    private Long userId;
    private Integer categoryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetOrderByType orderByType;
    private Integer offset;
    private Integer limit;
}
