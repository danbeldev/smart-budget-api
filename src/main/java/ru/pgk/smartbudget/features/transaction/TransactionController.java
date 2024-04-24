package ru.pgk.smartbudget.features.transaction;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.transaction.dto.TransactionDto;
import ru.pgk.smartbudget.features.transaction.dto.params.CreateTransactionParams;
import ru.pgk.smartbudget.features.transaction.mappers.TransactionMapper;
import ru.pgk.smartbudget.features.transaction.services.TransactionService;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @GetMapping
    private PageDto<TransactionDto> getAll(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ) {
        return PageDto.fromPage(
                transactionService.getAllByUserId(userId, offset, limit).map(transactionMapper::toDto)
        );
    }

    @PostMapping
    private void create(
            @RequestParam Long userId,
            @RequestBody CreateTransactionParams params
    ) {
        transactionService.create(userId, params);
    }
}
