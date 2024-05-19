package ru.pgk.smartbudget.features.transaction.mappers.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pgk.smartbudget.features.expenseCategory.mappers.ExpenseCategoryMapper;
import ru.pgk.smartbudget.features.transaction.dto.chart.TransactionChartDto;
import ru.pgk.smartbudget.features.transaction.dto.chart.TransactionChartPointDto;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionChartMapper {

    private final ExpenseCategoryMapper expenseCategoryMapper;

    public List<TransactionChartDto> toDto(List<TransactionEntity> transactions) {
        Map<LocalDate, List<TransactionChartPointDto>> chartPointsByDate = transactions.stream()
                .collect(Collectors.groupingBy(TransactionEntity::getDate,
                        Collectors.mapping(transaction ->
                                        new TransactionChartPointDto(transaction.getAmountInBaseCurrency(),
                                                expenseCategoryMapper.toDto(transaction.getCategory())),
                                Collectors.toList())));

        return chartPointsByDate.entrySet().stream()
                .map(entry -> new TransactionChartDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<TransactionChartPointDto> toDtoPoints(List<TransactionEntity> transactions) {
        return transactions.stream()
                .map(transaction -> new TransactionChartPointDto(
                        transaction.getAmountInBaseCurrency(),
                        expenseCategoryMapper.toDto(transaction.getCategory())))
                .collect(Collectors.toList());
    }

}
