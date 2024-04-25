package ru.pgk.smartbudget.features.budget.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pgk.smartbudget.common.dto.PageDto;
import ru.pgk.smartbudget.features.budget.dto.BudgetDto;
import ru.pgk.smartbudget.features.budget.dto.params.CreateOrUpdateBudgetParams;
import ru.pgk.smartbudget.features.budget.mappers.BudgetMapper;
import ru.pgk.smartbudget.features.budget.services.BudgetService;
import ru.pgk.smartbudget.security.jwt.JwtEntity;

@RestController
@RequestMapping("budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    private final BudgetMapper budgetMapper;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<BudgetDto> getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "0") @Min(0) @Max(100) Integer limit,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return PageDto.fromPage(
                budgetService.getAllByUserId(jwtEntity.getUserId(), offset, limit).map(budgetMapper::toDto)
        );
    }

    @GetMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private BudgetDto getById(
            @PathVariable Long id
    ) {
        return budgetMapper.toDto(budgetService.getById(id));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    private BudgetDto create(
            @RequestBody CreateOrUpdateBudgetParams params,
            @AuthenticationPrincipal JwtEntity jwtEntity
    ) {
        return budgetMapper.toDto(budgetService.create(jwtEntity.getUserId(), params));
    }

    @PutMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void update(
            @PathVariable Long id,
            @RequestBody CreateOrUpdateBudgetParams params
    ) {
        budgetService.update(id, params);
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(
            @PathVariable Long id
    ) {
        budgetService.deleteById(id);
    }
}
