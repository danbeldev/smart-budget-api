package ru.pgk.smartbudget.features.transaction.dto.params;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetTransactionsParams {

    private Long userId;
    private String search;
    private Short currencyCodeId;
    private Integer categoryId;
    private LocalDate startDate;
    private LocalDate endDate;

    private TransactionOrderByType orderByType;

    private Integer offset = 0;
    private Integer limit = 20;
}
