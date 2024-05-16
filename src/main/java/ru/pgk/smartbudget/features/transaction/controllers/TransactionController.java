package ru.pgk.smartbudget.features.transaction.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.transaction.dto.TransactionDto;
import ru.pgk.smartbudget.features.transaction.dto.chart.TransactionChartDto;
import ru.pgk.smartbudget.features.transaction.dto.chart.TransactionChartPointDto;
import ru.pgk.smartbudget.features.transaction.dto.params.CreateTransactionParams;
import ru.pgk.smartbudget.features.transaction.dto.params.GetTransactionsParams;
import ru.pgk.smartbudget.features.transaction.dto.params.TransactionOrderByType;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.transaction.mappers.TransactionMapper;
import ru.pgk.smartbudget.features.transaction.mappers.chart.TransactionChartMapper;
import ru.pgk.smartbudget.features.transaction.services.TransactionService;
import ru.pgk.smartbudget.features.transaction.services.report.TransactionReportService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

import java.time.LocalDate;
import java.util.List;

@Valid
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction", description = "Information about the user's financial transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionReportService transactionReportService;

    private final TransactionMapper transactionMapper;
    private final TransactionChartMapper transactionChartMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<TransactionDto> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Short currencyCodeId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) TransactionOrderByType orderByType,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit,
            @AuthenticationPrincipal JwtEntity jwt
    ) {
        GetTransactionsParams params = new GetTransactionsParams();
        params.setUserId(jwt.getUserId());
        params.setSearch(search);
        params.setCategoryId(categoryId);
        params.setCurrencyCodeId(currencyCodeId);
        params.setStartDate(startDate);
        params.setEndDate(endDate);
        params.setOrderByType(orderByType);
        params.setOffset(offset);
        params.setLimit(limit);

        return PageDto.fromPage(transactionService.getAll(params).map(transactionMapper::toDto));
    }

    @GetMapping("report.xls")
    @SecurityRequirement(name = "bearerAuth")
    private ResponseEntity<byte[]> generatedReport(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @AuthenticationPrincipal JwtEntity jwt
    ) {
        return transactionReportService.generatedReport(jwt.getUserId(), startDate, endDate);
    }

    @GetMapping("charts")
    @SecurityRequirement(name = "bearerAuth")
    private List<TransactionChartDto> getChartsData(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @AuthenticationPrincipal JwtEntity jwt
    ) {
        List<TransactionEntity> transactions = transactionService.getAllByDate(jwt.getUserId(), startDate, endDate);
        return transactionChartMapper.toDto(transactions);
    }

    @GetMapping("chart")
    @SecurityRequirement(name = "bearerAuth")
    private List<TransactionChartPointDto> getChartData(
            @RequestParam LocalDate date,
            @AuthenticationPrincipal JwtEntity jwt
    ) {
        List<TransactionEntity> transactions = transactionService.getAllByDate(jwt.getUserId(), date, date);
        return transactionChartMapper.toDtoPoints(transactions);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            description = "currencyCodeId if null, then the base currency RUB is taken"
    )
    private TransactionDto create(
            @RequestBody CreateTransactionParams params,
            @AuthenticationPrincipal JwtEntity jwt
    ) {
        return transactionMapper.toDto(transactionService.create(jwt.getUserId(), params));
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(
            @PathVariable Long id
    ) {
        transactionService.deleteById(id);
    }
}
