package ru.pgk.smartbudget.features.transaction.mappers.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.transaction.dto.chart.TransactionChartDto;
import ru.pgk.smartbudget.features.transaction.dto.chart.TransactionChartPointDto;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionChartMapper {

    private final ExpenseCategoryMapper expenseCategoryMapper;

    public List<TransactionChartDto> toDto(List<TransactionEntity> transactions) {
        Map<LocalDate, List<TransactionEntity>> transactionsByDate = transactions.stream().collect(
                Collectors.groupingBy(TransactionEntity::getDate));

        List<TransactionChartDto> transactionChart = new ArrayList<>();

        // TODO: grouping by category and currency
        transactionsByDate.forEach((date, values) -> {
            List<TransactionChartPointDto> points = new ArrayList<>();
            values.forEach(value -> {
                TransactionChartPointDto point = new TransactionChartPointDto(
                        value.getAmountInBaseCurrency(), expenseCategoryMapper.toDto(value.getCategory()));
                points.add(point);
            });
            TransactionChartDto transactionChartDto = new TransactionChartDto(date, points);
            transactionChart.add(transactionChartDto);
        });

        return transactionChart;
    }

    public List<TransactionChartPointDto> toDtoPoints(List<TransactionEntity> transactions) {
        List<TransactionChartPointDto> points = new ArrayList<>();
        transactions.forEach(transaction -> {
            TransactionChartPointDto point = new TransactionChartPointDto(
                    transaction.getAmountInBaseCurrency(), expenseCategoryMapper.toDto(transaction.getCategory()));
            points.add(point);
        });
        return points;
    }
}
