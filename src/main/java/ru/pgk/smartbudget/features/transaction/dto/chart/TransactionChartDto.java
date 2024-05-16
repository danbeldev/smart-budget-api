package ru.pgk.smartbudget.features.transaction.dto.chart;

import java.time.LocalDate;
import java.util.List;

public record TransactionChartDto(
        LocalDate date,
        List<TransactionChartPointDto> points
) {}
