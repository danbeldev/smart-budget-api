package ru.pgk.smartbudget.features.transaction;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.transaction.dto.TransactionDto;
import ru.pgk.smartbudget.features.transaction.dto.params.CreateTransactionParams;
import ru.pgk.smartbudget.features.transaction.mappers.TransactionMapper;
import ru.pgk.smartbudget.features.transaction.services.TransactionService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@Valid
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<TransactionDto> getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit,
            @AuthenticationPrincipal JwtEntity jwt
    ) {
        return PageDto.fromPage(
                transactionService.getAllByUserId(jwt.getUserId(), offset, limit).map(transactionMapper::toDto)
        );
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
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
