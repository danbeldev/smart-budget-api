package ru.pgk.smartbudget.features.transaction.controllers.recurring;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.transaction.dto.recurring.RecurringTransactionDto;
import ru.pgk.smartbudget.features.transaction.dto.recurring.params.CreateRecurringTransactionParams;
import ru.pgk.smartbudget.features.transaction.mappers.recurring.RecurringTransactionMapper;
import ru.pgk.smartbudget.features.transaction.services.recurring.RecurringTransactionService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@RestController
@RequestMapping("recurring-transactions")
@RequiredArgsConstructor
@Tag(
        name = "Recurring transaction",
        description = "Contains information about the user's recurring transactions, such as monthly or weekly expenses (Under development)"
)
public class RecurringTransactionController {

    private final RecurringTransactionService recurringTransactionService;

    private final RecurringTransactionMapper recurringTransactionMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<RecurringTransactionDto> getAll(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(100) Integer limit,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return PageDto.fromPage(
                recurringTransactionService.getAll(jwtEntity.getUserId(), offset, limit)
                        .map(recurringTransactionMapper::toDto)
        );
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    private RecurringTransactionDto add(
            @RequestBody CreateRecurringTransactionParams params,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return recurringTransactionMapper.toDto(recurringTransactionService.add(jwtEntity.getUserId(), params));
    }

    @PatchMapping("{id}/is-achieved")
    @SecurityRequirement(name = "bearerAuth")
    private void updateIsAchieved(
            @PathVariable Long id,
            @RequestParam Boolean isAchieved,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        recurringTransactionService.updateIsAchieved(id, isAchieved);
    }
}
