package ru.pgk.smartbudget.features.transaction.controllers.recurring;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.transaction.dto.recurring.RecurringTransactionDto;
import ru.pgk.smartbudget.features.transaction.dto.recurring.params.CreateRecurringTransactionParams;
import ru.pgk.smartbudget.features.transaction.mappers.recurring.RecurringTransactionMapper;
import ru.pgk.smartbudget.features.transaction.services.recurring.RecurringTransactionService;
import ru.pgk.smartbudget.security.expressions.CustomSecurityExpression;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@Valid
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

    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<RecurringTransactionDto> getAll(

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "offset must be greater than or equal to zero")
            Integer offset,

            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "limit must be greater than or equal to one")
            @Max(value = 100, message = "limit must be less than or equal to one hundred")
            Integer limit,

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
            @Validated @RequestBody CreateRecurringTransactionParams params,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return recurringTransactionMapper.toDto(recurringTransactionService.add(jwtEntity.getUserId(), params));
    }

    @PatchMapping("{id}/make-inactive")
    @SecurityRequirement(name = "bearerAuth")
    private void makeInactive(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(!customSecurityExpression.canAccessRecurringTransaction(jwtEntity.getUserId(), id))
            throw new AccessDeniedException("Access is denied");

        recurringTransactionService.makeInactive(id);
    }
}
