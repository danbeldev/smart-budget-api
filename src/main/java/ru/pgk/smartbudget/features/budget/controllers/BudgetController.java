package ru.pgk.smartbudget.features.budget.controllers;

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
import ru.pgk.smartbudget.features.budget.dto.BudgetDto;
import ru.pgk.smartbudget.features.budget.dto.params.BudgetOrderByType;
import ru.pgk.smartbudget.features.budget.dto.params.CreateOrUpdateBudgetParams;
import ru.pgk.smartbudget.features.budget.dto.params.GetBudgetsParams;
import ru.pgk.smartbudget.features.budget.mappers.BudgetMapper;
import ru.pgk.smartbudget.features.budget.services.BudgetService;
import ru.pgk.smartbudget.security.expressions.CustomSecurityExpression;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

import java.time.LocalDate;

@Valid
@RestController
@RequestMapping("budgets")
@RequiredArgsConstructor
@Tag(name = "Budget", description = "Information about the budget limits set by the user for the categories of expenses")
public class BudgetController {

    private final BudgetService budgetService;

    private final BudgetMapper budgetMapper;

    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<BudgetDto> getAll(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) BudgetOrderByType orderByType,

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "offset must be greater than or equal to zero")
            Integer offset,

            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "limit must be greater than or equal to one")
            @Max(value = 100, message = "limit must be less than or equal to one hundred")
            Integer limit,

            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        GetBudgetsParams params = new GetBudgetsParams();
        params.setUserId(jwtEntity.getUserId());
        params.setCategoryId(categoryId);
        params.setStartDate(startDate);
        params.setEndDate(endDate);
        params.setOrderByType(orderByType);
        params.setOffset(offset);
        params.setLimit(limit);

        return PageDto.fromPage(budgetService.getAll(params).map(budgetMapper::toDto));
    }

    @GetMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private BudgetDto getById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(!customSecurityExpression.canAccessBudget(jwtEntity.getUserId(), id))
            throw new AccessDeniedException("Access is denied");

        return budgetMapper.toDto(budgetService.getById(id));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    private BudgetDto create(
            @Validated @RequestBody CreateOrUpdateBudgetParams params,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return budgetMapper.toDto(budgetService.create(jwtEntity.getUserId(), params));
    }

    @PutMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void update(
            @PathVariable Long id,
            @Validated @RequestBody CreateOrUpdateBudgetParams params,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(!customSecurityExpression.canAccessBudget(jwtEntity.getUserId(), id))
            throw new AccessDeniedException("Access is denied");

        budgetService.update(id, params);
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        if(!customSecurityExpression.canAccessBudget(jwtEntity.getUserId(), id))
            throw new AccessDeniedException("Access is denied");

        budgetService.deleteById(id);
    }
}
